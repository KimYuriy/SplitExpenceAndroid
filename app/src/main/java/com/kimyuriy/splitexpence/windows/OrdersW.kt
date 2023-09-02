package com.kimyuriy.splitexpence.windows

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.kimyuriy.splitexpence.R
import com.kimyuriy.splitexpence.adapters.BillItemAdapter
import com.kimyuriy.splitexpence.adapters.DishAdapter
import com.kimyuriy.splitexpence.adapters.PeopleSelectionItemAdapter
import com.kimyuriy.splitexpence.databinding.OrdersWBinding
import com.kimyuriy.splitexpence.dataclasses.DishDataClass
import com.kimyuriy.splitexpence.utils.Store

class OrdersW : AppCompatActivity(), DishAdapter.OnDeleteListener {
    private lateinit var b: OrdersWBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = OrdersWBinding.inflate(layoutInflater)
        setContentView(b.root)

        /**
         * Установка адаптера для RecyclerView
         */
        val dishesAdapter = DishAdapter(Store.dishesLiveData.value ?: ArrayList())
        b.dishesRV.layoutManager = LinearLayoutManager(this@OrdersW)
        b.dishesRV.adapter = dishesAdapter

        /**
         * Установка слушателя интерфейса удаления блюда
         */
        dishesAdapter.setOnDishDeletedListener(this)

        /**
         * Наблюдение за массивом блюд для RecyclerView
         */
        Store.dishesLiveData.observe(this) {
            dishesAdapter.update(it)
        }

        /**
         * Кнопка показа BottomSheetDialog добавления человека
         */
        b.addPersonBtn.setOnClickListener {
            val dialogView = LayoutInflater.from(this).inflate(R.layout.add_new_person_bottom_sheet, null)
            val dialog = BottomSheetDialog(this)
            dialogView.findViewById<Button>(R.id.addPersonBtn).setOnClickListener {
                val name = dialogView.findViewById<EditText>(R.id.personNameET).text.toString()
                Store.addPerson(name)
                dialog.cancel()
            }
            dialog.setContentView(dialogView)
            dialog.show()
        }

        /**
         * Кнопка показа BottomSheetDialog добавления блюда
         */
        b.addDishBtn.setOnClickListener {
            Store.clearSelectedPeople()
            val dialogView = LayoutInflater.from(this).inflate(R.layout.add_new_dish_bottom_sheet, null)
            val dialog = BottomSheetDialog(this)
            val rv = dialogView.findViewById<RecyclerView>(R.id.peopleSelectionRV)
            val selectionAdapter = PeopleSelectionItemAdapter(Store.getPeopleDataArray())
            rv.layoutManager = LinearLayoutManager(this@OrdersW, LinearLayoutManager.HORIZONTAL, false)
            rv.adapter = selectionAdapter
            dialogView.findViewById<Button>(R.id.addDishBtn).setOnClickListener {
                val name = dialogView.findViewById<EditText>(R.id.dishNameET).text.toString()
                val count = dialogView.findViewById<EditText>(R.id.dishCountET).text.toString().toInt()
                val price = dialogView.findViewById<EditText>(R.id.dishPriceET).text.toString().toInt()
                Store.addDish(name, count, price)
                dialog.cancel()
            }
            dialog.setContentView(dialogView)
            dialog.show()
        }

        /**
         * Кнопка показа BottomSheetDialog отображения текущего чека
         */
        b.showBillBtn.setOnClickListener {
            val dialogView = LayoutInflater.from(this).inflate(R.layout.bill_bottom_sheet, null)
            val dialog = BottomSheetDialog(this)
            val rv: RecyclerView = dialogView.findViewById(R.id.billRV)
            dialogView.findViewById<TextView>(R.id.totalBillTV).text = "Итого: ${Store.getTotalSum()} р"
            rv.layoutManager = LinearLayoutManager(this)
            rv.adapter = BillItemAdapter(Store.getPeopleDataArray())
            dialog.setContentView(dialogView)
            dialog.show()
        }
    }

    /**
     * Переопределенная функция удаления блюда
     */
    override fun onDelete(dish: DishDataClass) {
        Store.removeDish(dish)
    }
}