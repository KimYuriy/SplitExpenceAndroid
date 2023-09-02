package com.kimyuriy.splitexpence.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kimyuriy.splitexpence.R
import com.kimyuriy.splitexpence.dataclasses.PersonDataClass

class BillItemAdapter(private val array: ArrayList<PersonDataClass>): RecyclerView.Adapter<BillItemAdapter.ViewHolder>() {
    inner class ViewHolder(i: View): RecyclerView.ViewHolder(i) {
        val billTV: TextView = i.findViewById(R.id.billTV)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.bill_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.apply {
            billTV.text = "${array[position].name}: ${(array[position].sumToPay).toInt()} р"
        }
    }

    override fun getItemCount() = array.size
}