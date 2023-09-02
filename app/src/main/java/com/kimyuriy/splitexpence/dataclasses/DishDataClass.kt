package com.kimyuriy.splitexpence.dataclasses

data class DishDataClass(val id: Int, val name: String, val count: Int, val whoOrdered: ArrayList<SelectedPersonDataClass>, val price: Int)
