package br.com.zup.chave

import br.com.zup.KeyManagerGrpcServiceGrpc
import br.com.zup.chave.cadastrar.CadastrarChaveHttpRequest
import br.com.zup.chave.cadastrar.CadastrarChaveHttpResponse
import br.com.zup.chave.validators.ValidUUID
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Post
import io.micronaut.validation.Validated
import javax.inject.Inject
import javax.validation.Valid

@Validated
@Controller("/api/v1")
class ChavePixController(
    @Inject private val gRpcClient: KeyManagerGrpcServiceGrpc.KeyManagerGrpcServiceBlockingStub
){

    @Post("/cliente/{idCliente}/pix")
    fun cadastrar(
        @Valid request: CadastrarChaveHttpRequest,
        @PathVariable @ValidUUID idCliente: String
    ): HttpResponse<*> {
        val response = gRpcClient.cadastrar(request.paraGrpc(idCliente))
        return HttpResponse.created(CadastrarChaveHttpResponse(response.pixId))
    }
}