package com.example.mad_unhinged

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ChatActivity : AppCompatActivity() {

    private val db = Firebase.firestore
    private val auth = Firebase.auth
    private lateinit var chatRecyclerView: RecyclerView
    private lateinit var messageEditText: EditText
    private lateinit var sendButton: Button
    private lateinit var adapter: ChatAdapter

    private var matchId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        matchId = intent.getStringExtra("matchId")

        chatRecyclerView = findViewById(R.id.chat_recycler_view)
        messageEditText = findViewById(R.id.message_edit_text)
        sendButton = findViewById(R.id.send_button)

        setupRecyclerView()
        listenForMessages()

        sendButton.setOnClickListener {
            sendMessage()
        }
    }

    private fun setupRecyclerView() {
        adapter = ChatAdapter()
        chatRecyclerView.adapter = adapter
        chatRecyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun listenForMessages() {
        matchId?.let {
            db.collection("matches").document(it).collection("messages")
                .orderBy("timestamp")
                .addSnapshotListener { snapshots, e ->
                    if (e != null) {
                        return@addSnapshotListener
                    }

                    val messages = snapshots?.toObjects(Message::class.java)
                    if (messages != null) {
                        adapter.setMessages(messages)
                    }
                }
        }
    }

    private fun sendMessage() {
        val messageText = messageEditText.text.toString()
        if (messageText.isNotEmpty()) {
            val currentUser = auth.currentUser
            if (currentUser != null) {
                val message = Message(
                    senderId = currentUser.uid,
                    text = messageText,
                    timestamp = System.currentTimeMillis()
                )

                matchId?.let {
                    db.collection("matches").document(it).collection("messages")
                        .add(message)

                    messageEditText.text.clear()
                }
            }
        }
    }
}