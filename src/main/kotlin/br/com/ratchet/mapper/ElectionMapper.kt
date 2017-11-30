package br.com.ratchet.mapper

import br.com.ratchet.client.model.ElectionData
import br.com.ratchet.controller.model.ElectionPost
import br.com.ratchet.controller.model.ElectionRequest
import br.com.ratchet.repository.model.Candidate
import br.com.ratchet.repository.model.Election
import br.com.ratchet.repository.model.Post
import br.com.ratchet.repository.model.Vote
import java.util.Collections.singletonList

fun toElection(electionRequest: ElectionRequest, electionData: List<ElectionData>): Election {
    val candidateData = groupElectionDataByCandidate(electionData)
    val candidates = toCandidates(candidateData)

    return Election(electionRequest.year, toPost(electionRequest.post, candidates))
}

private fun groupElectionDataByCandidate(electionData: List<ElectionData>) =
    electionData.groupBy {
        Candidate(it.candidateNumber, it.candidateName, it.partyAcronym, it.partyNumber, emptyList())
    }

private fun toCandidates(candidateData: Map<Candidate, List<ElectionData>>) =
    candidateData.mapValues {
        val votes = it.value.map {
            Vote(it.state, it.shift, it.numberVotes)
        }
        Candidate(it.key.number, it.key.name, it.key.partyAcronym, it.key.partyNumber, votes)
    }.values.toList()

private fun toPost(electionPost: ElectionPost, candidates: List<Candidate>) =
    singletonList(Post(electionPost.code, electionPost.description, candidates))
