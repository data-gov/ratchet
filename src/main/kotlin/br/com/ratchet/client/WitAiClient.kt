package br.com.ratchet.client

import br.com.ratchet.client.model.EntityValues
import br.com.ratchet.client.model.WitAiEntityRequest
import br.com.ratchet.configuration.feign.FeignConfiguration
import org.springframework.cloud.netflix.feign.FeignClient
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(name = "wit.ai", url = "\${witai.api}", configuration = arrayOf(FeignConfiguration::class))
interface WitAiClient {

    @PutMapping(path = arrayOf("/entities/{entityId}?v=20170307"))
    fun updatePresidents(@RequestBody request: WitAiEntityRequest, @RequestParam("entityId") entityId: String?, @RequestHeader("Authorization") token: String)

    @PostMapping(path = arrayOf("/entities/{entityId}/values?v=20170307"))
    fun addNewEntityValue(@RequestParam("entityId") entityId: String, @RequestBody request: EntityValues, @RequestHeader("Authorization") token: String)
}
