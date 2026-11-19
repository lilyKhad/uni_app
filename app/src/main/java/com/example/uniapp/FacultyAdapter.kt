package com.example.uniapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FacultyAdapter(
    private var items: List<Faculty>,
    private val onClick: (Faculty) -> Unit
) : RecyclerView.Adapter<FacultyAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.tvFacultyName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_faculty, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.name.setText(item.name)
        holder.itemView.setOnClickListener { onClick(item) }
    }

    override fun getItemCount() = items.size

    fun update(newItems: List<Faculty>) {
        items = newItems
        notifyDataSetChanged()
    }
}