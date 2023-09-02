package com.kimyuriy.splitexpence.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kimyuriy.splitexpence.R
import com.kimyuriy.splitexpence.dataclasses.PersonDataClass
import com.kimyuriy.splitexpence.dataclasses.SelectedPersonDataClass
import com.kimyuriy.splitexpence.utils.Store

class PeopleSelectionItemAdapter(private val array: ArrayList<PersonDataClass>): RecyclerView.Adapter<PeopleSelectionItemAdapter.ViewHolder>() {
    inner class ViewHolder(i: View): RecyclerView.ViewHolder(i) {
        private val selectionCB: CheckBox = i.findViewById(R.id.personSelectCB)
        val nameTV: TextView = i.findViewById(R.id.personNameTV)

        init {
            selectionCB.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    Store.addPersonToSelected(SelectedPersonDataClass(array[position].id, array[position].name))
                }
                else {
                    Store.removePersonFromSelected(SelectedPersonDataClass(array[position].id, array[position].name))
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.people_selection_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.apply {
            nameTV.text = array[position].name
        }
    }

    override fun getItemCount() = array.size
}