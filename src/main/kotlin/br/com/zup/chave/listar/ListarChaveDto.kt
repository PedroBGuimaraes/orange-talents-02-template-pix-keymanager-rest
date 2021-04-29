package br.com.zup.chave.listar

import br.com.zup.ListarChaveResponse
import br.com.zup.chave.utils.TipoChave
import br.com.zup.chave.utils.TipoConta
import com.fasterxml.jackson.annotation.JsonFormat
import io.micronaut.core.annotation.Introspected
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

@Introspected
data class ListarChaveHttpResponse(
    val chaves: List<Chave>,
    val idCliente: String,
) {
    data class Chave(
        val idPix: String,
        val tipoChave: TipoChave,
        val tipoConta: TipoConta,
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
        val criadaEm: LocalDateTime,
    )
}

fun ListarChaveResponse.toHttpResponse(): List<ListarChaveHttpResponse.Chave> {
    return this.chavesList.map { chave ->
        ListarChaveHttpResponse.Chave(
            idPix = chave.idPix,
            tipoChave = TipoChave.valueOf(chave.tipoChave.name),
            tipoConta = TipoConta.valueOf(chave.tipoConta.name),
            criadaEm = chave.criadaEm.let {
                Instant
                    .ofEpochSecond(it.seconds, it.nanos.toLong())
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime()
            }
        )
    }
}