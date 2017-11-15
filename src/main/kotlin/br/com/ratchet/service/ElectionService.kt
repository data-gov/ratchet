package br.com.ratchet.service

import br.com.ratchet.client.ElectionClient
import mu.KotlinLogging
import org.springframework.stereotype.Service

@Service
class ElectionService(private val client: ElectionClient) {

    private val logger = KotlinLogging.logger {}

    fun extractElectionInfo(roleCode: Int = 1, electionYears: List<Int> = listOf(1998, 2002, 2006, 2010, 2014)) {
        val electionData = client.electionData(electionYears.joinToString(), roleCode)

        logger.info { "Election data extracted successfully. ${electionData.size} results found." }
    }

}
