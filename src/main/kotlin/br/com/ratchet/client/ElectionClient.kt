package br.com.ratchet.client

import br.com.ratchet.client.model.ElectionData
import br.com.ratchet.configuration.feign.election.configuration.ElectionClientConfiguration
import org.springframework.cloud.netflix.feign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(name = "election", url = "\${cepesp.api}", configuration = arrayOf(ElectionClientConfiguration::class))
interface ElectionClient {

    @GetMapping(path = arrayOf("/tse"))
    fun electionData(@RequestParam(name = "ano") year: Int,
                     @RequestParam(name = "cargo") post: Int,
                     @RequestParam(name = "agregacao_regional") regionalAggregation: Int = 2,
                     @RequestParam(name = "agregacao_politica") politicalAggregation: Int = 2): List<ElectionData>
}
