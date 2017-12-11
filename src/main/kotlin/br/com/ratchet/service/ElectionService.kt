package br.com.ratchet.service

import br.com.ratchet.client.ElectionClient
import br.com.ratchet.controller.model.ElectionRequest
import br.com.ratchet.mapper.toElection
import br.com.ratchet.repository.ElectionRepository
import br.com.ratchet.repository.model.Election
import mu.KotlinLogging
import org.springframework.stereotype.Service

@Service
class ElectionService(private val client: ElectionClient,
                      private val repository: ElectionRepository,
                      private val witai: WitAiService) {

    private val logger = KotlinLogging.logger {}

    fun extractElectionInfo(request: ElectionRequest): Election {
        val electionData = client.electionData(request.year, request.post.code)
        val election = toElection(request, electionData)

        logger.info { "Election data generated successfully. Sending data to Wit.Ai..." }
        witai.saveCandidates(election)

        logger.info { "WitAi data has sent successfully. Sending data to mongo..." }
        val savedData = repository.save(election)

        logger.info { "Mongo data data has sent successfully! Extraction complete !" }
        return savedData
    }

}
