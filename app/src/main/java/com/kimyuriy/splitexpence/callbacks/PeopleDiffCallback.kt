package com.kimyuriy.splitexpence.callbacks

import androidx.recyclerview.widget.DiffUtil
import com.kimyuriy.splitexpence.dataclasses.PersonDataClass

class PeopleDiffCallback(private val old: ArrayList<PersonDataClass>, private val new: ArrayList<PersonDataClass>): DiffUtil.Callback() {
    override fun getOldListSize() = old.size

    override fun getNewListSize() = new.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) = old[oldItemPosition] == new[newItemPosition]

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) = old[oldItemPosition] == new[newItemPosition]
}