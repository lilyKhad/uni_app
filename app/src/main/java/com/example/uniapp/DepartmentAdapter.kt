package com.example.uniapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DepartmentAdapter(
    private var items: List<Department>,
    private val onClick: (Department) -> Unit
) : RecyclerView.Adapter<DepartmentAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.tvDepartmentName)
        val faculty: TextView = view.findViewById(R.id.tvDepartmentFaculty)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_department, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.name.setText(item.name)
        holder.faculty.setText(item.facultyName)
        holder.itemView.setOnClickListener { onClick(item) }
    }

    override fun getItemCount() = items.size

    fun update(newItems: List<Department>) {
        items = newItems
        notifyDataSetChanged()
    }
}