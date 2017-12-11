package br.com.ratchet.service

import br.com.ratchet.client.WitAiClient
import br.com.ratchet.client.model.EntityValues
import br.com.ratchet.client.model.WitAiEntityRequest
import br.com.ratchet.client.model.WitAiException
import br.com.ratchet.repository.model.Candidate
import br.com.ratchet.repository.model.Election
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

@Service
class WitAiService(private val client: WitAiClient) {

    private val logger = KotlinLogging.logger {}
    private val roleEntityMap = hashMapOf("PRESIDENTE" to "president")

    @Value("Bearer \${witai.token}")
    private lateinit var token: String

    fun saveCandidates(election: Election) {
        election.post.forEach { post ->
            if (post.postDescription == "PRESIDENTE") {
                saveWholeEntity(post.candidates, post.postDescription)
            }
        }
    }

    private fun saveWholeEntity(candidates: List<Candidate>, role: String) {
        val newCandidatesEntity = candidates.map { candidate -> toEntityValue(candidate.name) }
        val entityId = roleEntityMap[role].orEmpty()
        val actualCandidatesEntity = client.getEntities(entityId, token).values
        val request = WitAiEntityRequest(entityId, actualCandidatesEntity.plus(newCandidatesEntity))

        try {
            client.saveEntity(request, entityId, token)
            logger.info { "Wit Ai ${request.id} updated!" }
        } catch (exception: WitAiException) {
            if (exception.status == HttpStatus.CONFLICT) {
                logger.error { "there's a ${request.id} saved, skipping this one" }
            }
        }
    }

    private fun toEntityValue(name: String) = EntityValues(name, synonyms(name), metadata(name))
    private fun metadata(name: String) = name.replace(' ', '_')
    private fun synonyms(name: String) = name.split(' ').filter({ word -> !(word == "DA" || word == "DE") })

}
