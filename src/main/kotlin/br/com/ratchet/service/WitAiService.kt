package br.com.ratchet.service

import br.com.ratchet.client.WitAiClient
import br.com.ratchet.client.model.EntityValues
import br.com.ratchet.repository.model.Candidate
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
                savePresidents(post.candidates)
            }
        }
    }

    private fun savePresidents(candidates: List<Candidate>) {
        candidates.forEach { candidate ->
            try {
                client.addNewPresident(toEntityValue(candidate.name), "Bearer $token")
                logger.info { "Added $candidate.name to Wit.ai president entity" }
            } catch (exception: RuntimeException) {
                if (exception.message == "WIT AI Conflict") {
                    logger.info { "there's a ${candidate.name} saved, skipping this one" }
                }
            }
        }
    }

    private fun toEntityValue(name: String) = EntityValues(name, synonyms(name), metadata(name))

    private fun synonyms(name: String): List<String> {
        val commomNames: (String) -> Boolean = { word -> !(word == "DA" || word == "DE") }
        return name.split(' ').filter(commomNames)
    }

    private fun metadata(name: String) = name.replace(' ', '_')

}
