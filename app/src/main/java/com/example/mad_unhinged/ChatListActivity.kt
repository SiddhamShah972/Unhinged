package com.example.mad_unhinged

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView

class ChatListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_list)

        val conversationsRecyclerView: RecyclerView = findViewById(R.id.rvConversations)

        // TODO:
        // 1. Create a RecyclerView.Adapter for the conversations list.
        // 2. Set the adapter for conversationsRecyclerView.
        // 3. Like the matches page, the click logic will be in the adapter.
    }
}