package br.com.ratchet.service

import br.com.ratchet.client.ElectionClient
import br.com.ratchet.client.model.ElectionData
import br.com.ratchet.controller.model.ElectionPost
import br.com.ratchet.controller.model.ElectionRequest
import br.com.ratchet.repository.ElectionRepository
import br.com.ratchet.repository.model.Candidate
import br.com.ratchet.repository.model.Election
import br.com.ratchet.repository.model.Post
import br.com.ratchet.repository.model.Vote
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit4.SpringRunner
import java.util.Collections.singletonList

@RunWith(SpringRunner::class)
internal class ElectionServiceTest {

    @MockBean private lateinit var client: ElectionClient
    @MockBean private lateinit var repository: ElectionRepository

    private lateinit var service: ElectionService

    @Before
    fun setUp() {
        service = ElectionService(client, repository)
    }

    @Test
    fun shouldExtractElectionInfo() {
        val electionRequest = ElectionRequest(2014, ElectionPost.PRESIDENT)
        val expectedElectionInfo = expectedData()

        whenever(client.electionData(any(), any(), any(), any())).thenReturn(electionData())
        whenever(repository.save(expectedElectionInfo)).thenReturn(expectedElectionInfo)

        val actualElectionInfo = service.extractElectionInfo(electionRequest)

        verify(client).electionData(2014, 1)
        verify(repository).save(expectedData())

        assertThat(actualElectionInfo).isEqualToComparingFieldByField(expectedElectionInfo)
    }

    private fun expectedData(): Election {
        val candidateOne = Candidate(99, "VINICIUS", "PDG", 99,
            listOf(Vote("RS", 1, 100), Vote("SP", 1, 5), Vote("RS", 2, 15), Vote("SP", 2, 5)))

        val candidateTwo = Candidate(88, "MENINO MORONI", "PPP", 88,
            listOf(Vote("RS", 1, 50), Vote("SP", 1, 2)))

        return Election(2014, singletonList(Post(1, "PRESIDENTE", listOf(candidateOne, candidateTwo))))
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

}
