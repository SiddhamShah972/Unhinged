package com.example.mad_unhinged

data class Message(
    val senderId: String = "",
    val text: String = "",
    val timestamp: Long = 0
)