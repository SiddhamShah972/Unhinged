package com.example.mad_unhinged

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class HomeFragment : Fragment() {

    private lateinit var usersRecyclerView: RecyclerView
    private lateinit var userListAdapter: UserListAdapter
    private val db = Firebase.firestore
    private val auth = Firebase.auth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        usersRecyclerView = view.findViewById(R.id.users_recycler_view)
        usersRecyclerView.layoutManager = LinearLayoutManager(context)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fetchUsers()
    }

    private fun fetchUsers() {
        val currentUserId = auth.currentUser?.uid
        db.collection("users")
            .whereNotEqualTo("uid", currentUserId) // Exclude the current user
            .get()
            .addOnSuccessListener { result ->
                val users = result.toObjects(User::class.java)
                userListAdapter = UserListAdapter(users) { user ->
                    // Handle like button click
                    handleLike(user.uid)
                }
                usersRecyclerView.adapter = userListAdapter
            }
    }

    private fun handleLike(likedUserId: String) {
        val currentUserId = auth.currentUser?.uid
        if (currentUserId != null) {
            val likesRef = db.collection("users").document(currentUserId).collection("likes")
            likesRef.document(likedUserId).set(mapOf("timestamp" to System.currentTimeMillis()))

            // Check for a match
            db.collection("users").document(likedUserId).collection("likes").document(currentUserId).get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        // It's a match!
                        val match = mapOf(
                            "users" to listOf(currentUserId, likedUserId),
                            "timestamp" to System.currentTimeMillis()
                        )
                        db.collection("matches").add(match)
                    }
                }
        }
    }
}