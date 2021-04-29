package br.com.zup.chave.cadastrar

import br.com.zup.CadastrarChaveRequest
import br.com.zup.chave.utils.TipoConta
import br.com.zup.chave.validators.ValidPixKey
import io.micronaut.core.annotation.Introspected
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Introspected
@ValidPixKey
data class CadastrarChaveHttpRequest(
    @field:NotNull
    val tipoChave: br.com.zup.chave.utils.TipoChave?,
    @field:NotNull
    val tipoConta: TipoConta?,
    @field:Size(max = 77)
    val chave: String?,
) {
    fun paraGrpc(idCliente: String): CadastrarChaveRequest? {
        return CadastrarChaveRequest.newBuilder()
            .setChave(chave)
            .setIdCliente(idCliente)
            .setTipoChave(br.com.zup.TipoChave.valueOf(tipoChave!!.name))
            .setTipoConta(br.com.zup.TipoConta.valueOf(tipoConta!!.name))
            .build()
    }
}

data class CadastrarChaveHttpResponse(
    val pixId: String,
)