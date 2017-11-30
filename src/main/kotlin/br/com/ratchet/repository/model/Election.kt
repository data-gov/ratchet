package br.com.ratchet.repository.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "elections")
data class Election(
    @Id val year: Int,
    val post: List<Post>
)

data class Post(
    @Id val postCode: Int,
    val postDescription: String,
    val candidates: List<Candidate>
)

data class Candidate(
    @Id val number: Int,
    val name: String,
    val partyAcronym: String,
    val partyNumber: Int,
    val votes: List<Vote>
)

data class Vote(
    val state: String,
    val shift: Int,
    val quantity: Int
)
