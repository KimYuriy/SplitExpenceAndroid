package com.kimyuriy.splitexpence.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.kimyuriy.splitexpence.R
import com.kimyuriy.splitexpence.callbacks.DishDiffCallback
import com.kimyuriy.splitexpence.dataclasses.DishDataClass

class DishAdapter(private val array: ArrayList<DishDataClass>): RecyclerView.Adapter<DishAdapter.ViewHolder>() {
    interface OnDeleteListener {
        fun onDelete(dish: DishDataClass)
    }

    private var onDishDeletedListener: OnDeleteListener? = null
    fun setOnDishDeletedListener(listener: OnDeleteListener) {
        onDishDeletedListener = listener
    }

    inner class ViewHolder(i: View): RecyclerView.ViewHolder(i) {
        private val deleteDishBtn: ImageButton = i.findViewById(R.id.deleteDishBtn)
        val dishTV: TextView = i.findViewById(R.id.dishTV)

        init {
            deleteDishBtn.setOnClickListener {
                val pos = adapterPosition
                if (pos != RecyclerView.NO_POSITION) {
                    onDishDeletedListener?.onDelete(array[pos])
                }
            }

            i.setOnClickListener {
                val dialogView = LayoutInflater.from(i.context).inflate(R.layout.who_selected_bottom_sheet, null)
                val bs = BottomSheetDialog(i.context)
                dialogView.findViewById<TextView>(R.id.dishNameTV).text = array[adapterPosition].name
                val rv: RecyclerView = dialogView.findViewById(R.id.selectedPeopleRV)
                rv.apply {
                    layoutManager = LinearLayoutManager(i.context)
                    adapter = SelectedPersonItemAdapter(array[adapterPosition].whoOrdered)
                }
                bs.setContentView(dialogView)
                bs.show()
            }
        }
    }

    fun update(newArray: ArrayList<DishDataClass>) {
        val df = DiffUtil.calculateDiff(DishDiffCallback(array, newArray))
        array.clear()
        array.addAll(newArray)
        df.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.dish_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.apply {
            dishTV.text = "${array[position].name} x${array[position].count}: ${array[position].price} р"
        }
    }

    override fun getItemCount() = array.size
}