package com.example.eater


data class DishData(
    val id: Int=0,
    val url:String="",
    val name: String="",
    val price: Int=0,
    val contents: String="",
    val type: String="",
    val count:Int=0,
    val orderId:Int=0
)

data class AllDishes(
    val dishes:List<DishData>?=null
)