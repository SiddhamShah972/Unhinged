package com.example.mad_unhinged

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MatchesActivity : AppCompatActivity() {

    private val db = Firebase.firestore
    private val auth = Firebase.auth
    private lateinit var matchesRecyclerView: RecyclerView
    private lateinit var adapter: MatchesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_matches)

        matchesRecyclerView = findViewById(R.id.matches_recycler_view)
        setupRecyclerView()
        fetchMatches()
    }

    private fun setupRecyclerView() {
        adapter = MatchesAdapter(emptyList()) { match ->
            val intent = Intent(this, ChatActivity::class.java)
            intent.putExtra("matchId", match.id)
            startActivity(intent)
        }
        matchesRecyclerView.adapter = adapter
        matchesRecyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun fetchMatches() {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            db.collection("matches")
                .whereArrayContains("users", currentUser.uid)
                .get()
                .addOnSuccessListener { result ->
                    val matches = result.toObjects(Match::class.java)
                    adapter.setMatches(matches)
                }
        }
    }
}