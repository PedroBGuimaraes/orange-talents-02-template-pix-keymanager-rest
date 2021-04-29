package br.com.zup.chave

import br.com.zup.*
import br.com.zup.chave.cadastrar.CadastrarChaveHttpRequest
import br.com.zup.chave.cadastrar.CadastrarChaveHttpResponse
import br.com.zup.chave.detalhar.BuscarChaveHttpResponse
import br.com.zup.chave.detalhar.toHttpResponse
import br.com.zup.chave.listar.ListarChaveHttpResponse
import br.com.zup.chave.listar.toHttpResponse
import br.com.zup.chave.validators.ValidUUID
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.*
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

    @Delete("/cliente/{idCliente}/pix/{idPix}")
    fun remover(
        @PathVariable @ValidUUID idPix: String,
        @PathVariable @ValidUUID idCliente: String,
    ): HttpResponse<Unit>{
        val grpcRequest = RemoverChaveRequest.newBuilder()
            .setIdChave(idPix)
            .setIdCliente(idCliente)
            .build()
        gRpcClient.remover(grpcRequest)
        return HttpResponse.ok()
    }

    @Get("/cliente/{idCliente}/pix")
    fun listar(
        @PathVariable @ValidUUID idCliente: String,
    ): HttpResponse<ListarChaveHttpResponse> {
        val response = gRpcClient.listar(
            ListarChaveRequest.newBuilder()
                .setIdCliente(idCliente)
                .build()
        )

        return HttpResponse.ok(ListarChaveHttpResponse(response.toHttpResponse(), idCliente))
    }

    @Get("/cliente/{idCliente}/pix/{idPix}")
    fun detalhar(
        @PathVariable @ValidUUID idCliente: String,
        @PathVariable @ValidUUID idPix: String,
    ): HttpResponse<BuscarChaveHttpResponse> {
        val response: BuscarChaveResponse = gRpcClient.buscar(
            BuscarChaveRequest.newBuilder()
                .setPixId(
                    BuscarChaveRequest.BuscaPorPixId.newBuilder()
                        .setIdChave(idPix)
                        .setIdCliente(idCliente)
                ).build()
        )

        return HttpResponse.ok(response.toHttpResponse())
    }

}