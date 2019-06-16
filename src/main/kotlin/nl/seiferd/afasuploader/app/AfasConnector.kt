package nl.seiferd.afasuploader.app

import com.sun.deploy.net.HttpRequest
import khttp.post
import khttp.responses.Response


class AfasConnector(val abboId: String, val token: String) {

    private val AFAS_TOKEN = "AfasToken"
    private val AUTHORIZATION = "Authorization"

    fun authorizationHeader(token: String): String {
        return "$AFAS_TOKEN $token"
    }

    fun sendToAfas(index: Int, message: String) : Response {
        return post(
                buildUrl(),
                mapOf(AUTHORIZATION to authorizationHeader(token), HttpRequest.CONTENT_TYPE to "application/json", "batch" to index.toString()),
                data = message
        )
    }

    private fun buildUrl(): String =
            "https://$abboId.rest.afas.online/ProfitRestServices/connectors/KnSubject"

}