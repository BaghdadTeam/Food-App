package logic.usecase
import io.mockk.every
import io.mockk.mockk
import logic.MealsProvider
import model.Meal
import org.example.utils.EmptyMealsException
import com.google.common.truth.Truth.assertThat
import org.example.logic.MockInputProvider
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class GuessMealGameUseCaseTest {

 private val mealsProvider = mockk<MealsProvider>()
 private val inputProvider = MockInputProvider(listOf("20", "45"))

 @Test
 fun `should return correct result when user guesses correctly`() {
  // given
  val meal = mockk<Meal>()
  every { meal.name } returns "Pizza"
  every { meal.preparationTime } returns 45
  every { mealsProvider.getMeals() } returns listOf(meal)

  val inputProvider = MockInputProvider(listOf("45"))
  val useCase = GuessMealGameUseCase(mealsProvider, inputProvider)

  // when
  val result = useCase.startGame(inputProvider::provideInput)

  // then
  assertThat(result.isSuccessful).isTrue()
  assertThat(result.message).isEqualTo("Correct! The preparation time is 45 minutes.")
 }

 @Test
 fun `should handle invalid input gracefully and return failure after max attempts`() {
  // given
  val meal = mockk<Meal>()
  every { meal.name } returns "Pizza"
  every { meal.preparationTime } returns 45
  val mealsProvider = mockk<MealsProvider>()
  every { mealsProvider.getMeals() } returns listOf(meal)

  val inputProvider = MockInputProvider(listOf("invalid", "invalid", "invalid"))
  val useCase = GuessMealGameUseCase(mealsProvider, inputProvider)

  // when
  val result = useCase.startGame(inputProvider::provideInput)

  // then
  assertThat(result.isSuccessful).isFalse()
  assertThat(result.message).isEqualTo("The correct time was 45 minutes.")
 }
 @Test
 fun `should return failure result when user guesses incorrectly after three attempts`() {
  // given
  val meal = mockk<Meal>()
  every { meal.name } returns "Pizza"
  every { meal.preparationTime } returns 45
  val mealsProvider = mockk<MealsProvider>()
  every { mealsProvider.getMeals() } returns listOf(meal)

  val inputProvider = MockInputProvider(listOf("30", "50", "60"))
  val useCase = GuessMealGameUseCase(mealsProvider, inputProvider)

  // when
  val result = useCase.startGame(inputProvider::provideInput)

  // then
  assertThat(result.isSuccessful).isFalse()
  assertThat(result.message).isEqualTo("The correct time was 45 minutes.")
 }
 @Test
 fun `should throw EmptyMealsException when no meals are available`() {
  // given
  every { mealsProvider.getMeals() } returns emptyList()

  val useCase = GuessMealGameUseCase(mealsProvider, MockInputProvider(emptyList()))

  // when & then
  assertThrows<EmptyMealsException> {
   useCase.guessMealPreparationTime()
  }
 }

 @Test
 fun `should return a random meal when meals are available`() {
  // given
  val meal = mockk<Meal>()
  every { meal.name } returns "Pizza"
  every { meal.preparationTime } returns 45
  every { mealsProvider.getMeals() } returns listOf(meal)

  val useCase = GuessMealGameUseCase(mealsProvider, inputProvider)

  // when
  val result = useCase.guessMealPreparationTime()

  // then
  assertThat(result).isEqualTo(meal)
 }

 @Test
 fun `should throw EmptyMealsException when getMeals returns emptyList`() {
  // given
  val mealsProvider = mockk<MealsProvider>()
  every { mealsProvider.getMeals() } returns emptyList()

  val inputProvider = MockInputProvider(emptyList())
  val useCase = GuessMealGameUseCase(mealsProvider, inputProvider)

  // when & then
  assertThrows<EmptyMealsException> {
   useCase.guessMealPreparationTime()
  }
 }
}