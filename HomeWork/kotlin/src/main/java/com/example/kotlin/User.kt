package com.example.kotlin

class User (
        val name: String,
        var address: String,
        private val age: Int = 18
) {
    private var phone: String
    init {
        phone = "+375445555555"
    }
    constructor() : this("Vova", "street", 22)
    fun isAdult() = age == 18
}
