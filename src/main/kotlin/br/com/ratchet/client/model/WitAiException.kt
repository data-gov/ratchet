package br.com.ratchet.client.model

import org.springframework.http.HttpStatus

class WitAiException(override var message: String, var status: HttpStatus): Exception()
