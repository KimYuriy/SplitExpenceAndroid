package com.kimyuriy.splitexpence.utils

import androidx.lifecycle.MutableLiveData
import com.kimyuriy.splitexpence.dataclasses.DishDataClass
import com.kimyuriy.splitexpence.dataclasses.PersonDataClass
import com.kimyuriy.splitexpence.dataclasses.SelectedPersonDataClass
import kotlin.math.ceil
import kotlin.math.pow

object Store {
    /**
     * Массив данных людей - ID, имена и потраченные финансы
     */
    private val peopleDataArray = ArrayList<PersonDataClass>()

    /**
     * Массив данных блюд - ID, название, количество, ID и имена заказавших, стоимость
     */
    private val dishesDataArray = ArrayList<DishDataClass>()

    /**
     * Массив людей, выбравших конкретное блюдо
     */
    private val selectedPeople = ArrayList<SelectedPersonDataClass>()

    /**
     * ID добавляемого пользователя
     */
    private var id = -1

    /**
     * LiveData массива блюд - отображается в окне блюд
     */
    val dishesLiveData = MutableLiveData<ArrayList<DishDataClass>>()

    /**
     * Функция установки массива людей
     */
    fun setPeopleArray(array: ArrayList<PersonDataClass>) {
        peopleDataArray.addAll(array)
    }

    /**
     * Функция получения массива людей
     */
    fun getPeopleDataArray() = peopleDataArray

    /**
     * Функция добавления человека в массив людей, выбравших блюдо
     */
    fun addPersonToSelected(personID: SelectedPersonDataClass) {
        selectedPeople.add(personID)
    }

    /**
     * Функция удаления человека из массива людей, выбравших блюдо
     */
    fun removePersonFromSelected(personID: SelectedPersonDataClass) {
        selectedPeople.remove(personID)
    }

    /**
     * Функция очистки массива людей, выбравших блюдо
     */
    fun clearSelectedPeople() {
        if (selectedPeople.isNotEmpty()) selectedPeople.clear()
    }

    /**
     * Функция получения общей суммы
     */
    fun getTotalSum(): Int {
        var sum = 0
        for (item in dishesDataArray) {
            sum += item.price
        }
        return sum
    }

    /**
     * Функция установки ID из окна добавления людей
     */
    fun setLastID(lastID: Int) {
        id = lastID + 1
    }

    /**
     * Функция добавления человека в массив людей
     */
    fun addPerson(personName: String) {
        peopleDataArray.add(PersonDataClass(id, personName, 0f))
        id++
    }

    /**
     * Функция добавления блюда в массив блюд
     */
    fun addDish(name: String, count: Int, price: Int) {
        val dishID = ((Math.random() * 100).pow(2) + (Math.random() * 100).pow(3)).toInt()
        val selectedPeople1 = ArrayList<SelectedPersonDataClass>()
        selectedPeople1.addAll(selectedPeople)
        val dish = DishDataClass(dishID, name, count, selectedPeople1, price * count)
        dishesDataArray.add(dish)
        calculateSumForPeople(dish, true)
        dishesLiveData.value = dishesDataArray
        clearSelectedPeople()
    }

    /**
     * Функция удаления блюда из массива блюд
     */
    fun removeDish(dish: DishDataClass) {
        dishesDataArray.remove(dish)
        calculateSumForPeople(dish, false)
        dishesLiveData.value = dishesDataArray
    }

    /**
     * Функция расчета суммы выплат для каждого человека, заказавшего то или иное блюдо
     */
    private fun calculateSumForPeople(dish: DishDataClass, increasing: Boolean) {
        val avgPrice = ceil((dish.price / dish.whoOrdered.size).toDouble())
        for (selected in dish.whoOrdered) {
            for (i in peopleDataArray.indices) {
                if (selected.id == peopleDataArray[i].id) {
                    if (increasing) peopleDataArray[i].sumToPay += avgPrice.toFloat()
                    else peopleDataArray[i].sumToPay -= avgPrice.toFloat()
                }
            }
        }
    }
}