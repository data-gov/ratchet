package br.com.ratchet.configuration.feign.witai.configuration

import br.com.ratchet.configuration.feign.FeignConfiguration
import org.springframework.beans.factory.ObjectFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.http.HttpMessageConverters
import org.springframework.cloud.netflix.feign.support.ResponseEntityDecoder
import org.springframework.cloud.netflix.feign.support.SpringDecoder
import org.springframework.context.annotation.Bean

class WitaiClientConfiguration : FeignConfiguration() {

    @Autowired
    private lateinit var messageConverters: ObjectFactory<HttpMessageConverters>

    @Bean
    fun decoder() =
        ResponseEntityDecoder(SpringDecoder(this.messageConverters))

}
