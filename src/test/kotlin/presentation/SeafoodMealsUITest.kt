package org.example.presentation

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import logic.usecase.SeaFoodMealUseCase
import model.Meal
import org.example.utils.EmptyMealsException
import org.example.utils.NoMealFoundException
import org.junit.jupiter.api.Test

class SeafoodMealsUITest {

 private val useCase = mockk<SeaFoodMealUseCase>()
 private val viewer = mockk<Viewer>(relaxed = true)

 @Test
 fun `should display ranked seafood meals`() {
  // given
  val rankedMeals = listOf(
   Pair(1, createMeal("Shrimp Pasta", 30.0)),
   Pair(2, createMeal("Grilled Salmon", 20.0))
  )

  every { useCase.execute() } returns rankedMeals

  val ui = SeafoodMealsUI(useCase, viewer)

  // when
  ui.execute()

  // then
  verify { viewer.log("Rank    Name    Protein") }
  verify { viewer.log("1      Shrimp Pasta     30.0") }
  verify { viewer.log("2      Grilled Salmon     20.0") }
 }

 @Test
 fun `should handle EmptyMealsException`() {
  // given
  every { useCase.execute() } throws EmptyMealsException("No meals found")

  val ui = SeafoodMealsUI(useCase, viewer)

  // when
  ui.execute()

  // then
  verify { viewer.log("There is no meals in database") }
 }

 @Test
 fun `should handle NoMealFoundException`() {
  // given
  every { useCase.execute() } throws NoMealFoundException("There is no Sea Food Meals")

  val ui = SeafoodMealsUI(useCase, viewer)

  // when
  ui.execute()

  // then
  verify { viewer.log("There is no seafood meals found") }
 }

 @Test
 fun `should handle general Exception`() {
  // given
  every { useCase.execute() } throws Exception("Something went wrong")

  val ui = SeafoodMealsUI(useCase, viewer)

  // when
  ui.execute()

  // then
  verify { viewer.log("There is a problem happened when retrieving the data.") }
 }

 private fun createMeal(name: String, protein: Double): Meal {
  val meal = mockk<Meal>()
  every { meal.name } returns name
  every { meal.nutrition?.protein } returns protein
  return meal
 }
}