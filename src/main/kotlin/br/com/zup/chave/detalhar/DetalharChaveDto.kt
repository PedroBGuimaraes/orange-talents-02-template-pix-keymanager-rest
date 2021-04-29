package br.com.zup.chave.detalhar

import br.com.zup.BuscarChaveResponse
import br.com.zup.chave.utils.TipoChave
import br.com.zup.chave.utils.TipoConta
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

data class BuscarChaveHttpResponse(
    val idCliente: String,
    val idPix: String,
    val chave: ChaveResponse,
)

data class ContaResponse(
    val tipo: TipoConta,
    val instituicao: String,
    val titularNome: String,
    val titularCpf: String,
    val agencia: String,
    val numero: String,
)

data class ChaveResponse(
    val tipo: TipoChave,
    val chave: String,
    val criadaEm: LocalDateTime,
    val conta: ContaResponse,
)

fun BuscarChaveResponse.toHttpResponse(): BuscarChaveHttpResponse {
    return BuscarChaveHttpResponse(
        idCliente = this.idCliente,
        idPix = this.idPix,
        chave = ChaveResponse(
            tipo = TipoChave.valueOf(this.chave.tipo.name),
            chave = this.chave.chave,
            criadaEm = this.chave.criadaEm.let {
                Instant.ofEpochSecond(it.seconds, it.nanos.toLong())
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime()
            },
            conta = ContaResponse(
                tipo = TipoConta.valueOf(this.chave.conta.tipo.name),
                instituicao = this.chave.conta.instituicao,
                titularNome = this.chave.conta.titularNome,
                titularCpf = this.chave.conta.titularCpf,
                agencia = this.chave.conta.agencia,
                numero = this.chave.conta.conta,
            )
        )
    )
}