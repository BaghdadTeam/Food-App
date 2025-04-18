package org.example.presentation

import logic.usecase.FilterQuickHealthyMealsUseCase

class QuickHealthyMealsUI(private val useCase: FilterQuickHealthyMealsUseCase) : Feature {
    override val id: Int = FEATURE_ID
    override val name: String = FEATURE_NAME

    override fun execute() {

        println("Please enter the number of meals you want to see:")
        val countInput = readln().toIntOrNull()

        if (countInput == null) {
            println("Please enter a valid number.")
        } else {
            try {
                val meals = useCase.execute(countInput)
                if (meals.isEmpty()) {
                    println("No quick healthy meals found ")
                } else {
                    println("Quick & Healthy Meals:")
                    meals.forEach { println("- ${it.name}") }
                }
            } catch (e: NoSuchElementException) {
                println("There are no quick healthy meals available ")
            }
        }
    }

    companion object {
        const val FEATURE_ID = 1
        const val FEATURE_NAME = "Get healthy fast food meals"
    }
}