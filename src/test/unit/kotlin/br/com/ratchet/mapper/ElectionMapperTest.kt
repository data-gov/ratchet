package br.com.ratchet.mapper

import br.com.ratchet.client.model.ElectionData
import br.com.ratchet.controller.model.ElectionPost
import br.com.ratchet.controller.model.ElectionRequest
import br.com.ratchet.repository.model.Candidate
import br.com.ratchet.repository.model.Election
import br.com.ratchet.repository.model.Post
import br.com.ratchet.repository.model.Vote
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import java.util.Collections.singletonList

internal class ElectionMapperTest {

    @Test
    fun shouldMapElectionDataToElection() {
        val electionRequest = ElectionRequest(2014, ElectionPost.PRESIDENT)

        val actualElection = toElection(electionRequest, electionData())
        val expectedElection = expectedData()

        assertThat(actualElection).isEqualToComparingFieldByField(expectedElection)
    }

    private fun electionData() =
        listOf(
            ElectionData(1, "PDG", 1, "PRESIDENTE", 2014, 99, 99, "RS", "VINICIUS", 100),
            ElectionData(1, "PDG", 1, "PRESIDENTE", 2014, 99, 99, "SP", "VINICIUS", 5),
            ElectionData(1, "PDG", 2, "PRESIDENTE", 2014, 99, 99, "RS", "VINICIUS", 15),
            ElectionData(1, "PDG", 2, "PRESIDENTE", 2014, 99, 99, "SP", "VINICIUS", 5),
            ElectionData(1, "PPP", 1, "PRESIDENTE", 2014, 88, 88, "RS", "MENINO MORONI", 50),
            ElectionData(1, "PPP", 1, "PRESIDENTE", 2014, 88, 88, "SP", "MENINO MORONI", 2)
        )

    private fun expectedData(): Election {
        val candidateOne = Candidate(99, "VINICIUS", "PDG", 99,
            listOf(Vote("RS", 1, 100), Vote("SP", 1, 5), Vote("RS", 2, 15), Vote("SP", 2, 5)))

        val candidateTwo = Candidate(88, "MENINO MORONI", "PPP", 88,
            listOf(Vote("RS", 1, 50), Vote("SP", 1, 2)))

        return Election(2014, singletonList(Post(1, "PRESIDENTE", listOf(candidateOne, candidateTwo))))
    }
}
