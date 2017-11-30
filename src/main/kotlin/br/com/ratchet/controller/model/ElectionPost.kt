package br.com.ratchet.controller.model

data class ElectionRequest(
    val year: Int,
    val post: ElectionPost
)

enum class ElectionPost(val code: Int, val description: String) {
    PRESIDENT(1, "PRESIDENTE")
}

