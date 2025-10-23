package com.example.mad_unhinged

import com.google.firebase.firestore.DocumentId

data class Match(
    @DocumentId val id: String = "",
    val users: List<String> = emptyList(),
    val timestamp: Long = 0
)