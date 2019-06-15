package nl.seiferd.afasuploader.app

import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.ClientResponse
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono


class AfasConnector(val abboId: String, val token: String) {

    private val AFAS_TOKEN = "AfasToken"
    private val AUTHORIZATION = "Authorization"

    fun authorizationHeader(token: String): String {
        return "$AFAS_TOKEN $token"
    }

    fun sendToAfas(message: String): Mono<ClientResponse>? {
        return toWebClient()
                .post()
                .header(AUTHORIZATION, authorizationHeader(token))
                .body(BodyInserters.fromObject(message))
                .exchange()
                .also { println("I have just send $message") }
    }

    private fun toWebClient(): WebClient {
        return WebClient.builder()
                .baseUrl(buildUrl())
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build()
    }

    private fun buildUrl(): String =
            "https://$abboId.rest.afas.online/ProfitRestServices/connectors/KnSubject"


}