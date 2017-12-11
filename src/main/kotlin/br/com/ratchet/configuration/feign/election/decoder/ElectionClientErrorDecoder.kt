package br.com.ratchet.configuration.feign.election.decoder

import br.com.ratchet.client.model.WitAiException
import feign.Response
import feign.codec.ErrorDecoder
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import java.lang.Exception

class ElectionClientErrorDecoder : ErrorDecoder {
    private val logger = KotlinLogging.logger {}

    override fun decode(methodKey: String, response: Response): Exception {
        logger.error { errorMessage(methodKey, response) }

        if (response.request().url().contains("api.wit.ai")) {
            return WitAiException(response.reason(), HttpStatus.valueOf(response.status()))
        }

        return RuntimeException("External API failure")
    }

    private fun errorMessage(methodKey: String, response: Response): String {
        val responseBody = response.body().asReader()
        return "Error Status: ${response.status()}  Error body: $responseBody Executing: $methodKey"
    }

}
