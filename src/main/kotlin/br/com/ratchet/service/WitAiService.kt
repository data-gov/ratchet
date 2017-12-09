package br.com.ratchet.service

import br.com.ratchet.client.WitAiClient
import br.com.ratchet.client.model.EntityValues
import br.com.ratchet.repository.model.Election
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class WitAiService(private val client: WitAiClient) {

    private val logger = KotlinLogging.logger {}
    @Value("\${witai.token}")
    private lateinit var token: String

    fun saveCandidates(election: Election) {
        election.post.forEach { post ->
            if (post.postDescription == "PRESIDENTE") {
                post.candidates.forEach { candidate ->
                    client.addNewPresident(toEntityValue(candidate.name), "Bearer $token")
                    logger.info { "Added $candidate.name to Wit.ai president entity" }
                }
            }
        }
    }

    private fun toEntityValue(name: String) = EntityValues(name, synonyms(name).filter { word -> !(word == "DA" || word == "DE") }, metadata(name))
    private fun synonyms(name: String) = name.split(' ')
    private fun metadata(name: String) = name.replace(' ', '_')

}
