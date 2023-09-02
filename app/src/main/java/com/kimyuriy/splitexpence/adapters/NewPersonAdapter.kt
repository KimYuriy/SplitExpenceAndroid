package com.kimyuriy.splitexpence.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kimyuriy.splitexpence.R
import com.kimyuriy.splitexpence.callbacks.PeopleDiffCallback
import com.kimyuriy.splitexpence.dataclasses.PersonDataClass

class NewPersonAdapter(private val array: ArrayList<PersonDataClass>) : RecyclerView.Adapter<NewPersonAdapter.ViewHolder>() {
    interface OnDeleteListener {
        fun onDelete(person: PersonDataClass)
    }

    private var onPersonDeleteListener: OnDeleteListener? = null
    fun setOnPersonDeleteListener(listener: OnDeleteListener) {
        onPersonDeleteListener = listener
    }

    inner class ViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        private val deleteBtn: ImageButton = item.findViewById(R.id.deletePersonBtn)
        var nameTV: TextView = item.findViewById(R.id.personNameTV)

        init {
            deleteBtn.setOnClickListener {
                val pos = adapterPosition
                if (pos != RecyclerView.NO_POSITION) {
                    onPersonDeleteListener?.onDelete(array[pos])
                }
            }
        }
    }

    fun update(newArray: ArrayList<PersonDataClass>) {
        val df = DiffUtil.calculateDiff(PeopleDiffCallback(array, newArray))
        array.clear()
        array.addAll(newArray)
        df.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.new_person_input_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.apply {
            nameTV.text = array[position].name
        }
    }

    override fun getItemCount() = array.size
}