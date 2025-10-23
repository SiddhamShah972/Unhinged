package com.example.mad_unhinged

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MatchesAdapter(
    private var matches: List<Match> = emptyList(),
    private val onMatchClicked: (Match) -> Unit
) : RecyclerView.Adapter<MatchesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.item_match, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val match = matches[position]
        // TODO: Get the other user's name and display it
        holder.name.text = "Match name"
        holder.itemView.setOnClickListener {
            onMatchClicked(match)
        }
    }

    override fun getItemCount(): Int {
        return matches.size
    }

    fun setMatches(matches: List<Match>) {
        this.matches = matches
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.match_name)
    }
}