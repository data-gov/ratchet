package br.com.ratchet.configuration.feign.election.decoder

import br.com.ratchet.client.model.ElectionData
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.dataformat.csv.CsvMapper
import com.fasterxml.jackson.dataformat.csv.CsvSchema
import com.google.common.collect.ImmutableList
import com.google.common.collect.ImmutableList.copyOf
import feign.Response
import feign.Util
import feign.codec.Decoder
import java.lang.reflect.Type

class ElectionClientDecoder : Decoder {

    private val mapper = CsvMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    private val electionDataReader = mapper.readerFor(ElectionData::class.java)
    private val csvSchema = CsvSchema.emptySchema().withHeader()

    override fun decode(response: Response, type: Type): ImmutableList<ElectionData> {
        val responseString = Util.toString(response.body()?.asReader())
        val responseObject = when (responseString) {
            null -> emptyList()
            else -> electionDataReader.with(csvSchema).readValues<ElectionData>(responseString).readAll()
        }

        return copyOf(responseObject)
    }
}
