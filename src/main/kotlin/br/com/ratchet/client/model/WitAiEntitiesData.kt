package br.com.ratchet.client.model

import com.fasterxml.jackson.annotation.JsonProperty

data class WitAiEntityRequest(
    @JsonProperty("id") val id: String?,
    @JsonProperty("values") val values: List<EntityValues>
)

data class EntityValues(
    @JsonProperty("value") val name: String,
    @JsonProperty("expressions") val synonyms: List<String>,
    @JsonProperty("metadata") val metadata: String?
)

data class WitAiEntitiesResponse(
    @JsonProperty("builtin") val builtin: Boolean,
    @JsonProperty("exotic") val exotic: Boolean,
    @JsonProperty("doc") val doc: String,
    @JsonProperty("id") val id: String,
    @JsonProperty("lang") val lang: String,
    @JsonProperty("name") val name: String,
    @JsonProperty("lookups") val lookups: List<String>
)
