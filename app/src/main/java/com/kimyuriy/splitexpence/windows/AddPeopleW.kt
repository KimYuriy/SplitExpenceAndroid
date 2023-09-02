package com.kimyuriy.splitexpence.windows

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.kimyuriy.splitexpence.adapters.NewPersonAdapter
import com.kimyuriy.splitexpence.databinding.AddPeopleWBinding
import com.kimyuriy.splitexpence.dataclasses.PersonDataClass
import com.kimyuriy.splitexpence.utils.Store

class AddPeopleW : AppCompatActivity(), NewPersonAdapter.OnDeleteListener {
    private lateinit var b: AddPeopleWBinding
    private lateinit var peopleAdapter: NewPersonAdapter
    private val peopleLiveData = MutableLiveData<ArrayList<PersonDataClass>>()
    private val people = ArrayList<PersonDataClass>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = AddPeopleWBinding.inflate(layoutInflater)
        setContentView(b.root)

        /**
         * ID добавляемых пользователей
         */
        var id = 0

        /**
         * Установка адаптера RecyclerView и слушателя интерфейса удаления
         */
        peopleAdapter = NewPersonAdapter(peopleLiveData.value ?: ArrayList())
        b.peopleRV.layoutManager = LinearLayoutManager(this@AddPeopleW)
        b.peopleRV.adapter = peopleAdapter
        peopleAdapter.setOnPersonDeleteListener(this)

        /**
         * Установка наблюдения за LiveData-массивом для RecyclerView
         */
        peopleLiveData.observe(this) {
            peopleAdapter.update(it)
        }

        /**
         * Нажатие кнопки добавления человека
         */
        b.addPersonBtn.setOnClickListener {
            if (b.personNameET.text.isNotEmpty()) {
                people.add(PersonDataClass(id, b.personNameET.text.toString(), 0f))
                peopleLiveData.value = people
                b.personNameET.text.clear()
                b.nextPageBtn.visibility = if (people.size >= 1 && b.nextPageBtn.visibility == View.GONE) View.VISIBLE else View.GONE
                id++
            }
        }

        /**
         * Нажатие кнопки перехода на следующую страницу
         */
        b.nextPageBtn.setOnClickListener {
            Store.setPeopleArray(people)
            Store.setLastID(id)
            startActivity(Intent(this, OrdersW::class.java))
            finish()
        }
    }

    /**
     * Переопределенный метод интерфейса - удаление человека из добавленных
     */
    override fun onDelete(person: PersonDataClass) {
        people.remove(person)
        peopleLiveData.value = people
        b.nextPageBtn.visibility = if (people.size >= 1 && b.nextPageBtn.visibility == View.GONE) View.VISIBLE else View.GONE
    }
}