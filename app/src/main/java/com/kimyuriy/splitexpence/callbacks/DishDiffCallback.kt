package com.kimyuriy.splitexpence.callbacks

import androidx.recyclerview.widget.DiffUtil
import com.kimyuriy.splitexpence.dataclasses.DishDataClass

class DishDiffCallback(private val old: ArrayList<DishDataClass>, private val new: ArrayList<DishDataClass>): DiffUtil.Callback() {
    override fun getOldListSize() = old.size

    override fun getNewListSize() = new.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) = old[oldItemPosition] == new[newItemPosition]

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) = old[oldItemPosition] == new[newItemPosition]
}