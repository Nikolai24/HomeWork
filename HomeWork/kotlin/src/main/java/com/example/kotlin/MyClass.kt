package com.example.kotlin

class MyClass {
    companion object
    fun main(args: Array<String>) {
        val calculator: Calculator = CalculatorImpl()
        System.out.println("sum = ${calculator.sum(4, 8)}")
        System.out.println("div = ${calculator.div(4, 8)}")
        val user = User(name = "Igor", address = "13-12", age = 18)
        val user2 = User(name = "Igor", address = "13-12")
    }
}