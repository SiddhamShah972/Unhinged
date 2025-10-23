package com.example.mad_unhinged

import com.google.firebase.firestore.DocumentId

data class User(
    @DocumentId val uid: String = "",
    val name: String = "",
    val bio: String = "",
    val profileImageUrl: String = ""
)