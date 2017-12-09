package br.com.ratchet.client

import br.com.ratchet.client.model.EntityValues
import br.com.ratchet.client.model.WitAiEntityRequest
import br.com.ratchet.configuration.feign.FeignConfiguration
import org.springframework.cloud.netflix.feign.FeignClient
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader

// https://wit.ai/docs/http/20170307#post--entities-link
@FeignClient(name = "wit.ai", url = "\${witai.api}", configuration = arrayOf(FeignConfiguration::class))
interface WitAiClient {

    @PutMapping(path = arrayOf("/entities/president?v=20170307"))
    fun updatePresidents(@RequestBody request: List<WitAiEntityRequest>)

    @PostMapping(path = arrayOf("/entities/president/values?v=20170307"))
    fun addNewPresident(@RequestBody request: EntityValues, @RequestHeader("Authorization") token: String)
}
