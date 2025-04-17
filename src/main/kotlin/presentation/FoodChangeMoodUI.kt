package org.example.presentation

import logic.feature.Feature

class FoodChangeMoodConsoleUI(
    private val features: Map<Int, Feature>) {
    fun start() {
        showWelcome()
        presentFeatures()
    }

    private fun showWelcome() = println("Welcome to Food Change Mood!")

    private fun getUserInput(): Int? = readlnOrNull()?.toIntOrNull()

    fun presentFeatures() {
        while (true) {
            showOptions()
            val option = getUserInput() ?: 0

            if (option == 0) {
                println("Exiting... Goodbye!")
                break
            }

            features[option]?.execute() ?: println("Invalid input, please try again.")

        }
    }

    private fun showOptions() {
        println("\n=== Please enter one of the following numbers ===")
        features.values.sortedBy { it.number }.forEach {
            println("${it.number}. ${it.name}")
        }
        println("0. Exit")
        print("Enter your choice: ")
    }

}