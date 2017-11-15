package br.com.ratchet.controller

import br.com.ratchet.service.ElectionService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ElectionController(private val service: ElectionService) {

    @GetMapping(path = arrayOf("/election"))
    fun extract() = service.extractElectionInfo()
}


