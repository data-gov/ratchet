package br.com.ratchet.controller

import br.com.ratchet.controller.model.ElectionRequest
import br.com.ratchet.service.ElectionService
import org.springframework.http.HttpStatus.OK
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = arrayOf("/election"))
class ElectionController(private val service: ElectionService) {

    @PostMapping
    @ResponseStatus(OK)
    fun extract(@RequestBody request: ElectionRequest) = service.extractElectionInfo(request)
}


