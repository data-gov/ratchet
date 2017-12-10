package br.com.ratchet.client

import br.com.ratchet.client.model.EntityValues
import br.com.ratchet.client.model.WitAiEntityRequest
import br.com.ratchet.configuration.environment.INTEGRATION
import com.github.tomakehurst.wiremock.client.WireMock.equalTo
import com.github.tomakehurst.wiremock.client.WireMock.givenThat
import com.github.tomakehurst.wiremock.client.WireMock.ok
import com.github.tomakehurst.wiremock.client.WireMock.post
import com.github.tomakehurst.wiremock.client.WireMock.postRequestedFor
import com.github.tomakehurst.wiremock.client.WireMock.put
import com.github.tomakehurst.wiremock.client.WireMock.putRequestedFor
import com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo
import com.github.tomakehurst.wiremock.client.WireMock.urlMatching
import com.github.tomakehurst.wiremock.client.WireMock.verify
import com.github.tomakehurst.wiremock.junit.WireMockRule
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner

@ActiveProfiles(INTEGRATION)
@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
class WitAiClientIntegrationTest {

    companion object {
        const val ANY_URL = "/?.*"
        const val WIREMOCK_PORT = 9000
        const val ENTITY_ID = "president"
        const val TOKEN = "token"
    }

    @get:Rule
    val wireMockRule = WireMockRule(WIREMOCK_PORT)

    @Autowired
    private lateinit var client: WitAiClient

    @Before
    fun setUp() {
        givenThat(post(urlMatching(ANY_URL)).willReturn(ok()))
        givenThat(put(urlMatching(ANY_URL)).willReturn(ok()))
    }

    @Test
    fun shouldAddNewEntityValueToWitAi() {
        val requestEntity = EntityValues("Ilmo vini", listOf("Sinonimos"), "meta")
        client.addNewEntityValue(ENTITY_ID, requestEntity,TOKEN)

        verify(
            postRequestedFor(urlEqualTo("/entities/$ENTITY_ID/values?v=20170307"))
                .withHeader("Authorization", equalTo(TOKEN))
        )
    }

    @Test
    fun shouldSaveWitAiEntity() {
        val name = "Ilmo vini"
        val requestEntity = EntityValues(name, listOf("Sinonimos"), "meta")
        val request = WitAiEntityRequest(name, listOf(requestEntity))

        client.saveEntity(request, ENTITY_ID, TOKEN)

        verify(
            putRequestedFor(urlEqualTo("/entities/$ENTITY_ID?v=20170307"))
                .withHeader("Authorization", equalTo(TOKEN))
        )
    }

}
