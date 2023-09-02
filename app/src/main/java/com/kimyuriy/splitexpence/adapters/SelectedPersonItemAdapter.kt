package com.kimyuriy.splitexpence.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kimyuriy.splitexpence.R
import com.kimyuriy.splitexpence.dataclasses.SelectedPersonDataClass

class SelectedPersonItemAdapter(private val array: ArrayList<SelectedPersonDataClass>): RecyclerView.Adapter<SelectedPersonItemAdapter.ViewHolder>() {
    inner class ViewHolder(i: View): RecyclerView.ViewHolder(i) {
        val personNameTV: TextView = i.findViewById(R.id.personNameTV)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.selected_person_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.apply {
            personNameTV.text = array[position].name
        }
    }

    override fun getItemCount() = array.size
}