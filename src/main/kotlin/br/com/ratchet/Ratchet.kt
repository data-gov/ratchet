package br.com.ratchet

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.netflix.feign.EnableFeignClients

@EnableFeignClients
@SpringBootApplication
class Ratchet

fun main(args: Array<String>) {
    SpringApplication.run(Ratchet::class.java, *args)
}
