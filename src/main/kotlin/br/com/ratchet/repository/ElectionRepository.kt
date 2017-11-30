package br.com.ratchet.repository

import br.com.ratchet.repository.model.Election
import org.springframework.data.mongodb.repository.MongoRepository

interface ElectionRepository : MongoRepository<Election, String>
