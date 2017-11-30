package br.com.ratchet.configuration.feign.decoder

import br.com.ratchet.client.model.ElectionData
import feign.Response
import feign.Util.UTF_8
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import java.io.ByteArrayInputStream
import java.util.Collections.singletonList
import java.util.HashMap

class ElectionClientDecoderTest {

    @Test
    fun shouldDecodeResponseIntoElectionDataList() {
        val decoder = ElectionClientDecoder()
        val response = knownResponse()
        val decodedObject = decoder.decode(response, ElectionData::class.java)

        assertThat(decodedObject).hasSize(2)
        assertThat(decodedObject).containsExactlyInAnyOrder(
            ElectionData(1, "ADG", 1, "PRESIDENTE", 2014, 99, 99, "RS", "EDUARDO MORONI", 72256),
            ElectionData(1, "PDG", 1, "PRESIDENTE", 2014, 88, 88, "RS", "VINICIUS COSTA", 167603))
    }

    @Test
    fun shouldReturnEmptyListWhenResponseIsNull() {
        val decoder = ElectionClientDecoder()
        val response = nullBodyResponse()
        val decodedObject = decoder.decode(response, ElectionData::class.java)

        assertThat(decodedObject).isEmpty()
    }

    private fun knownResponse(): Response {
        val headers: HashMap<String, Collection<String>> = hashMapOf("Content-Type" to singletonList("text/csv"))
        val data = electionData()
        val inputStream = ByteArrayInputStream(data.toByteArray(UTF_8))

        return Response.builder()
            .status(200)
            .reason("OK")
            .headers(headers)
            .body(inputStream, data.length)
            .build()
    }

    private fun nullBodyResponse() =
        Response.builder()
            .status(200)
            .reason("OK")
            .headers(emptyMap())
            .build()

    private fun electionData() =
        StringBuilder()
            .appendln("CODIGO_CARGO,SIGLA_PARTIDO,NUM_TURNO,DESCRICAO_CARGO,ANO_ELEICAO,NUMERO_PARTIDO,NUMERO_CANDIDATO,UF,NOME_CANDIDATO,QTDE_VOTOS")
            .appendln("1,ADG,1,PRESIDENTE,2014,99,99,RS,EDUARDO MORONI,72256")
            .appendln("1,PDG,1,PRESIDENTE,2014,88,88,RS,VINICIUS COSTA,167603")
            .toString()
}


