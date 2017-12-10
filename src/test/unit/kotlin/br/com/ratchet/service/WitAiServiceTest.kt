package br.com.ratchet.service

import br.com.ratchet.client.WitAiClient
import br.com.ratchet.repository.model.Candidate
import br.com.ratchet.repository.model.Election
import br.com.ratchet.repository.model.Post
import br.com.ratchet.repository.model.Vote
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.verify
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit4.SpringRunner
import java.util.Collections.singletonList

@RunWith(SpringRunner::class)
internal class WitAiServiceTest {

    companion object {
        val candidateOne = Candidate(99, "VINICIUS", "PDG", 99,
            listOf(Vote("RS", 1, 100), Vote("SP", 1, 5), Vote("RS", 2, 15), Vote("SP", 2, 5)))

        val candidateTwo = Candidate(88, "MENINO MORONI", "PPP", 88,
            listOf(Vote("RS", 1, 50), Vote("SP", 1, 2)))

        val election = Election(2014, singletonList(Post(1, "PRESIDENTE", listOf(candidateOne, candidateTwo))))
    }

    @MockBean private lateinit var client: WitAiClient

    private lateinit var service: WitAiService

    @Before
    fun setUp() {
        service = WitAiService(client)
    }

    @Test
    @Ignore("To com preguica de fazer")
    fun shouldSavePresidencyCandidatesOneByOne() {
        service.saveCandidates(election)

        election.post.forEach { _ ->
            verify(client).addNewEntityValue(eq("president"), any(), any())
        }
    }

}
