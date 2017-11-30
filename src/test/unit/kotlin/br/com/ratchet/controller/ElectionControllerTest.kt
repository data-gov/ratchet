package br.com.ratchet.controller

import br.com.ratchet.configuration.environment.TEST
import br.com.ratchet.controller.model.ElectionPost
import br.com.ratchet.controller.model.ElectionRequest
import br.com.ratchet.repository.model.Candidate
import br.com.ratchet.repository.model.Election
import br.com.ratchet.repository.model.Post
import br.com.ratchet.repository.model.Vote
import br.com.ratchet.service.ElectionService
import com.fasterxml.jackson.databind.ObjectMapper
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType.APPLICATION_JSON_UTF8
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.util.Collections

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(TEST)
@RunWith(SpringRunner::class)
internal class ElectionControllerTest {

    @MockBean lateinit var service: ElectionService

    @Autowired lateinit var mockMvc: MockMvc
    @Autowired lateinit var mapper: ObjectMapper

    @Test
    fun shouldExtractElectionResults() {
        val electionRequest = ElectionRequest(2014, ElectionPost.PRESIDENT)
        val jsonRequest = mapper.writeValueAsString(electionRequest)

        whenever(service.extractElectionInfo(electionRequest)).thenReturn(expectedData())

        mockMvc.perform(post("/election").content(jsonRequest).contentType(APPLICATION_JSON_UTF8))
            .andExpect(status().isOk)
    }

    private fun expectedData(): Election {
        val candidateOne = Candidate(99, "VINICIUS", "PDG", 99,
            listOf(Vote("RS", 1, 100), Vote("SP", 1, 5), Vote("RS", 2, 15), Vote("SP", 2, 5)))

        val candidateTwo = Candidate(88, "MENINO MORONI", "PPP", 88,
            listOf(Vote("RS", 1, 50), Vote("SP", 1, 2)))

        return Election(2014, Collections.singletonList(Post(1, "PRESIDENTE", listOf(candidateOne, candidateTwo))))
    }
}
