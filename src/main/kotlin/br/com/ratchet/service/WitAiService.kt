package br.com.ratchet.service

import br.com.ratchet.client.WitAiClient
import br.com.ratchet.client.model.EntityValues
import br.com.ratchet.client.model.WitAiEntityRequest
import br.com.ratchet.repository.model.Candidate
import br.com.ratchet.repository.model.Election
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class WitAiService(private val client: WitAiClient) {

    private val logger = KotlinLogging.logger {}
    @Value("Bearer \${witai.token}")
    private lateinit var token: String
    val roleEntityMap = hashMapOf("PRESIDENTE" to "president")

    fun saveCandidates(election: Election) {
        election.post.forEach { post ->
            if (post.postDescription == "PRESIDENTE") {
                saveWitEntity(post.candidates, post.postDescription)
            }
        }
    }

    private fun saveWitEntity(candidates: List<Candidate>, role: String) {
        val candidatesEntity = candidates.map { candidate -> toEntityValue(candidate.name) }
        val entityId = roleEntityMap[role]
        val request = WitAiEntityRequest(entityId, candidatesEntity)

        try {
            client.updatePresidents(request, entityId, token)
            logger.info { "Wit Ai ${request.id} updated!" }
        } catch (exception: RuntimeException) {
            if (exception.message == "WIT AI Conflict") {
                logger.error { "there's a ${request.id} saved, skipping this one" }
            }
        }
    }

    private fun toEntityValue(name: String) = EntityValues(name, synonyms(name), metadata(name))
    private fun metadata(name: String) = name.replace(' ', '_')
    private fun synonyms(name: String): List<String> {
        val commonNames: (String) -> Boolean = { word -> !(word == "DA" || word == "DE") }
        return name.split(' ').filter(commonNames)
    }
}
