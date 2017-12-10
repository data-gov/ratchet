package br.com.ratchet.configuration.feign.election.configuration

import br.com.ratchet.configuration.feign.FeignConfiguration
import br.com.ratchet.configuration.feign.election.decoder.ElectionClientDecoder
import br.com.ratchet.configuration.feign.election.decoder.ElectionClientErrorDecoder
import org.springframework.context.annotation.Bean

class ElectionClientConfiguration : FeignConfiguration() {

    @Bean
    fun electionDecoder() = ElectionClientDecoder()

    @Bean
    fun errorDecoder() = ElectionClientErrorDecoder()
}
