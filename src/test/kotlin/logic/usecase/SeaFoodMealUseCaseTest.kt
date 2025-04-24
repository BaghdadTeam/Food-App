package logic.usecase
import io.mockk.every
import io.mockk.mockk
import logic.MealsProvider
import model.Meal
import org.example.utils.EmptyMealsException
import org.example.utils.NoMealFoundException
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals

class SeaFoodMealUseCaseTest {

 private val mealsProvider = mockk<MealsProvider>()

 @Test
 fun `should return sorted seafood meals by protein`() {
  // given
  val meal1 = mockk<Meal>()
  every { meal1.tags } returns listOf("seafood")
  every { meal1.nutrition?.protein } returns 20.0
  every { meal1.name } returns "Grilled Salmon"

  val meal2 = mockk<Meal>()
  every { meal2.tags } returns listOf("seafood")
  every { meal2.nutrition?.protein } returns 30.0
  every { meal2.name } returns "Shrimp Pasta"

  every { mealsProvider.getMeals() } returns listOf(meal1, meal2)

  val useCase = SeaFoodMealUseCase(mealsProvider)

  // when
  val result = useCase.execute()

  // then
  assertEquals(2, result.size)
  assertEquals("Shrimp Pasta", result[0].second.name) // First in the list (sorted descending)
  assertEquals("Grilled Salmon", result[1].second.name) // Second in the list
 }

 @Test
 fun `should throw EmptyMealsException when no meals are available`() {
  // given
  every { mealsProvider.getMeals() } returns emptyList()

  val useCase = SeaFoodMealUseCase(mealsProvider)

  // when & then
  assertThrows<EmptyMealsException> {
   useCase.execute()
  }
 }

 @Test
 fun `should throw NoMealFoundException when no seafood meals are found`() {
  // given
  val meal1 = mockk<Meal>()
  every { meal1.tags } returns listOf("vegetarian")
  every { meal1.nutrition?.protein } returns 10.0

  every { mealsProvider.getMeals() } returns listOf(meal1)

  val useCase = SeaFoodMealUseCase(mealsProvider)

  // when & then
  assertThrows<NoMealFoundException> {
   useCase.execute()
  }
 }
}