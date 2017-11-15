package br.com.ratchet.client

import br.com.ratchet.configuration.environment.INTEGRATION
import com.github.tomakehurst.wiremock.client.WireMock.equalTo
import com.github.tomakehurst.wiremock.client.WireMock.get
import com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor
import com.github.tomakehurst.wiremock.client.WireMock.givenThat
import com.github.tomakehurst.wiremock.client.WireMock.ok
import com.github.tomakehurst.wiremock.client.WireMock.urlMatching
import com.github.tomakehurst.wiremock.client.WireMock.verify
import com.github.tomakehurst.wiremock.junit.WireMockRule
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner
import java.nio.file.Files.readAllBytes
import java.nio.file.Paths

@ActiveProfiles(INTEGRATION)
@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
class ElectionClientIntegrationTest {

    companion object {
        const val PRESIDENT_FIXTURE_PATH = "src/test/integration/resources/fixtures/president-election-data.csv"
        const val ELECTION_ENDPOINT = "/tse/?.*"
        const val WIREMOCK_PORT = 9000
    }

    @get:Rule
    val wireMockRule = WireMockRule(WIREMOCK_PORT)

    @Autowired
    private lateinit var client: ElectionClient

    @Before
    fun setUp() {
        val electionData = String(readAllBytes(Paths.get(PRESIDENT_FIXTURE_PATH)))

        givenThat(
            get(urlMatching(ELECTION_ENDPOINT))
                .withQueryParam("ano", equalTo("1998, 2002, 2006, 2010, 2014"))
                .withQueryParam("cargo", equalTo("1"))
                .withQueryParam("agregacao_regional", equalTo("2"))
                .withQueryParam("agregacao_politica", equalTo("2"))
                .willReturn(ok(electionData))
        )
    }

    @Test
    fun shouldExtractElectionInfo() {
        val electionData = client.electionData("1998, 2002, 2006, 2010, 2014", 1)

        verify(
            getRequestedFor(urlMatching(ELECTION_ENDPOINT))
                .withQueryParam("ano", equalTo("1998, 2002, 2006, 2010, 2014"))
                .withQueryParam("cargo", equalTo("1"))
                .withQueryParam("agregacao_regional", equalTo("2"))
                .withQueryParam("agregacao_politica", equalTo("2"))
        )

        assertThat(electionData).isNotEmpty

    }

}
