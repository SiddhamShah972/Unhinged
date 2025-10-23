package com.example.mad_unhinged

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class UserListAdapter(
    private val users: List<User>,
    private val onLikeClicked: (User) -> Unit
) : RecyclerView.Adapter<UserListAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = users[position]
        holder.bind(user, onLikeClicked)
    }

    override fun getItemCount() = users.size

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.user_name)
        private val bioTextView: TextView = itemView.findViewById(R.id.user_bio)
        private val likeButton: Button = itemView.findViewById(R.id.like_button)

        fun bind(user: User, onLikeClicked: (User) -> Unit) {
            nameTextView.text = user.name
            bioTextView.text = user.bio
            likeButton.setOnClickListener { onLikeClicked(user) }
        }
    }
}