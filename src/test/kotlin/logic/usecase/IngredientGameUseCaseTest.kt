package logic.usecase

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import kotlinx.datetime.LocalDate
import logic.MealsProvider
import logic.helpers.createMealHelper
import model.Nutrition
import org.example.model.IngredientQuestion
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals


class IngredientGameUseCaseTest {
    private lateinit var mealsProvider: MealsProvider
    private lateinit var ingredientGameUseCase: IngredientGameUseCase

    val meals = listOf(
        createMealHelper(
            "Breakfast Pizza", 1, 100, LocalDate.parse("2022-01-01"), listOf("breakfast"),
            Nutrition(173.4, 18.0, 0.0, 17.0, 22.0, 35.0, 1.0), 3,
            listOf("Preheat", "Add toppings", "Bake"), "Quick pizza",
            listOf("Cheese", "Tomato", "Lettuce", "null", "Onion", "Beef"), 3, 30
        ),

        createMealHelper(
            "Chicken Alfredo", 2, 101, LocalDate.parse("2022-01-02"), listOf("dinner"),
            Nutrition(400.0, 25.0, 2.0, 30.0, 35.0, 5.0, 1.5), 4,
            listOf("Cook pasta", "Grill chicken", "null", "Mix together"), "Creamy pasta",
            listOf("Chicken", "Cream", "Parmesan", "Garlic", "Pasta", "Pepper"), 4, 45
        ),

        createMealHelper(
            "Beef Tacos", 3, 102, LocalDate.parse("2022-01-03"), listOf("mexican"),
            Nutrition(300.0, 20.0, 5.0, 20.0, 25.0, 4.0, 1.2), 3,
            listOf("Cook beef", "Prepare shells", "Assemble tacos"), "Spicy beef tacos",
            listOf("Beef", "Taco shells", "Lettuce", "Cheddar", "Tomato", "Onion"), 3, 25
        ),

        createMealHelper(
            "Veggie Stir Fry", 4, 103, LocalDate.parse("2022-01-04"), listOf("vegetarian"),
            Nutrition(250.0, 10.0, 0.0, 15.0, 30.0, 10.0, 1.3), 3,
            listOf("Chop veggies", "Stir fry", "Serve hot"), "Colorful and quick veggie stir fry",
            listOf("Broccoli", "Carrot", "Bell Pepper", "Soy Sauce", "Garlic", "Ginger"), 3, 20
        ),

        createMealHelper(
            "Salmon Bowl", 5, 104, LocalDate.parse("2022-01-05"), listOf("seafood"),
            Nutrition(350.0, 15.0, 1.0, 25.0, 28.0, 7.0, 1.6), 3,
            listOf("Grill salmon", "Cook rice", "Assemble bowl"), "Nutritious salmon bowl",
            listOf("Salmon", "Rice", "Avocado", "Cucumber", "Soy Sauce", "Sesame"), 3, 35
        ),

        createMealHelper(
            "Pancakes", 6, 105, LocalDate.parse("2022-01-06"), listOf("breakfast"),
            Nutrition(200.0, 5.0, 0.0, 6.0, 30.0, 8.0, 1.1), 2,
            listOf("Mix batter", "Cook on skillet"), "Fluffy pancakes",
            listOf("Flour", "Egg", "Milk", "Sugar", "Baking Powder", "Butter"), 2, 15
        ),

        createMealHelper(
            "Lentil Soup", 7, 106, LocalDate.parse("2022-01-07"), listOf("vegan", "soup"),
            Nutrition(180.0, 3.0, 0.0, 10.0, 25.0, 12.0, 1.2), 2,
            listOf("Boil ingredients", "Simmer"), "Comforting lentil soup",
            listOf("Lentils", "Carrot", "Celery", "Onion", "Garlic", "Tomato"), 2, 40
        ),

        createMealHelper(
            "Shrimp Scampi", 8, 107, LocalDate.parse("2022-01-08"), listOf("seafood", "italian"),
            Nutrition(370.0, 18.0, 1.0, 24.0, 30.0, 6.0, 1.4), 3,
            listOf("Cook pasta", "Sauté shrimp", "Combine with sauce"), "Buttery shrimp scampi",
            listOf("Shrimp", "Garlic", "Butter", "Parsley", "Lemon", "Spaghetti"), 3, 30
        ),

        createMealHelper(
            "Caesar Salad", 9, 108, LocalDate.parse("2022-01-09"), listOf("salad"),
            Nutrition(220.0, 15.0, 0.5, 7.0, 12.0, 3.0, 1.0), 2,
            listOf("Chop ingredients", "Toss with dressing"), "Classic Caesar salad",
            listOf("Lettuce", "Croutons", "Parmesan", "Caesar dressing", "Chicken"), 2, 10
        ),

        createMealHelper(
            "Steak and Potatoes", 10, 109, LocalDate.parse("2022-01-10"), listOf("dinner"),
            Nutrition(450.0, 25.0, 1.5, 35.0, 40.0, 5.0, 1.7), 3,
            listOf("Grill steak", "Boil potatoes", "Plate together"), "Hearty meal",
            listOf("Steak", "Potatoes", "Salt", "Pepper", "Butter"), 3, 50
        ),

        createMealHelper(
            "Fruit Smoothie", 11, 110, LocalDate.parse("2022-01-11"), listOf("drink", "breakfast"),
            Nutrition(150.0, 2.0, 0.0, 3.0, 30.0, 15.0, 0.9), 1,
            listOf("Blend all ingredients"), "Refreshing smoothie",
            listOf("Banana", "Strawberry", "Yogurt", "Honey", "Milk"), 1, 5
        ),

        createMealHelper(
            "Baked Ziti", 12, 111, LocalDate.parse("2022-01-12"), listOf("italian"),
            Nutrition(380.0, 20.0, 2.0, 22.0, 45.0, 8.0, 1.3), 4,
            listOf("Boil pasta", "Prepare sauce", "Layer with cheese", "Bake"), "Cheesy baked ziti",
            listOf("Ziti", "Tomato Sauce", "Mozzarella", "Ricotta", "Basil", "Onion"), 4, 40
        ),

        createMealHelper(
            "Quinoa Salad", 13, 112, LocalDate.parse("2022-01-13"), listOf("vegan", "salad"),
            Nutrition(240.0, 9.0, 0.0, 8.0, 35.0, 6.0, 1.1), 2,
            listOf("Cook quinoa", "Mix ingredients"), "Light quinoa salad",
            listOf("Quinoa", "Tomato", "Cucumber", "Mint", "Olive oil", "Lemon juice"), 2, 25
        ),

        createMealHelper(
            "Eggplant Parmesan",
            14,
            113,
            LocalDate.parse("2022-01-14"),
            listOf("vegetarian", "italian"),
            Nutrition(310.0, 15.0, 1.0, 12.0, 28.0, 5.0, 1.2),
            3,
            listOf("Fry eggplant", "Layer with cheese", "Bake"),
            "Crispy eggplant parmesan",
            listOf("Eggplant", "Parmesan", "Marinara", "Mozzarella", "Basil"),
            3,
            35
        ),

        createMealHelper(
            "Sushi Rolls", 15, 114, LocalDate.parse("2022-01-15"), listOf("japanese", "seafood"),
            Nutrition(280.0, 8.0, 0.0, 15.0, 36.0, 6.0, 1.0), 3,
            listOf("Cook rice", "Roll ingredients", "Slice and serve"), "Homemade sushi",
            listOf("Rice", "Seaweed", "Salmon", "Avocado", "Cucumber", "Soy Sauce"), 3, 50
        )
    )

    private val fixedOptions = IngredientQuestion(
        mealName = "Pizza",
        correctIngredient = "Cheese",
        options = listOf("Cheese", "lattice", "sugar")
    )

    @BeforeEach
    fun setUp() {
        mealsProvider = mockk(relaxed = true)
        ingredientGameUseCase = IngredientGameUseCase(mealsProvider)
    }

    @Test
    fun `should return true when answer is correct `() {
        every { mealsProvider.getMeals() } returns meals

        val result = ingredientGameUseCase.execute(fixedOptions, 1)

        assertThat(result).isEqualTo(true)
    }

    @Test
    fun `should increase score when answer is correct`() {
        every { mealsProvider.getMeals() } returns meals

        ingredientGameUseCase.execute(fixedOptions, 1)

        assertThat(ingredientGameUseCase.getScore()).isEqualTo(1000)
    }

    @Test
    fun `should increase score over multiple correct answers`() {
        every { mealsProvider.getMeals() } returns meals

        ingredientGameUseCase.execute(fixedOptions, 1)
        ingredientGameUseCase.execute(fixedOptions, 1)

        assertThat(ingredientGameUseCase.getScore()).isEqualTo(2000)
    }

    @Test
    fun `execute should return true when correct option is chosen`() {
        every { mealsProvider.getMeals() } returns meals

        val result = ingredientGameUseCase.execute(fixedOptions, 1)
        assertThat(result).isTrue()
    }


    @Test
    fun `should return false when invalid choice`() {
        every { mealsProvider.getMeals() } returns meals

        val result = ingredientGameUseCase.execute(fixedOptions, 3)
        assertThat(result).isEqualTo(false)
    }

    @Test
    fun `should return false for out of bounds index`() {
        every { mealsProvider.getMeals() } returns meals

        val result = ingredientGameUseCase.execute(fixedOptions, 10)
        assertThat(result).isFalse()
    }

    @Test
    fun `should return false when choice is null`() {
        every { mealsProvider.getMeals() } returns meals

        val result = ingredientGameUseCase.execute(fixedOptions, null)

        assertThat(result).isFalse()
    }

    @Test
    fun `should not increase score for invalid choice`() {
        every { mealsProvider.getMeals() } returns meals

        ingredientGameUseCase.execute(fixedOptions, 3)

        assertThat(ingredientGameUseCase.getScore()).isEqualTo(0)
    }


    @Test
    fun `isGameOver should return true when reached 15 try`() {
        every { mealsProvider.getMeals() } returns meals
        ingredientGameUseCase = IngredientGameUseCase(mealsProvider) // Ensure the test starts fresh

        repeat(15) {
            val options = ingredientGameUseCase.getOptions()
            val choice = options!!.options.indexOf(options.correctIngredient) + 1
            ingredientGameUseCase.execute(options, choice)
        }

        val isOver = ingredientGameUseCase.isGameOver()
        assertTrue(isOver)
    }

    @Test
    fun `isGameOver should return score of 15000 after 15 correct answers`() {
        every { mealsProvider.getMeals() } returns meals
        ingredientGameUseCase = IngredientGameUseCase(mealsProvider) // Ensure the test starts fresh

        repeat(15) {
            val options = ingredientGameUseCase.getOptions()
            val choice = options!!.options.indexOf(options.correctIngredient) + 1
            ingredientGameUseCase.execute(options, choice)
        }

        assertThat(ingredientGameUseCase.getScore()).isEqualTo(15000)
    }

    @Test
    fun `isGameOver should return false when correctAnswers are less than 15`() {
        repeat(10) {
            val q = ingredientGameUseCase.getOptions()
            ingredientGameUseCase.execute(
                fixedOptions,
                q?.options?.indexOf(q.correctIngredient)?.plus(1)
            )
        }
        assertThat(ingredientGameUseCase.isGameOver()).isEqualTo(false)
    }


    @Test
    fun `getOptions should return null when no unused meals are available`() {
        every { mealsProvider.getMeals() } returns meals

        repeat(1) { ingredientGameUseCase.getOptions() }

        val result = ingredientGameUseCase.getOptions()

        assertEquals(null, result)
    }

    @Test
    fun `getOptions should return 3 unique options`() {
        every { mealsProvider.getMeals() } returns meals
        ingredientGameUseCase = IngredientGameUseCase(mealsProvider) // why?

        val options = ingredientGameUseCase.getOptions()

        assertEquals(3, options?.options?.distinct()?.size)
    }

    @Test
    fun `getOptions should return null if no meals available`() {
        every { mealsProvider.getMeals() } returns emptyList()

        val options = ingredientGameUseCase.getOptions()

        assertThat(options).isNull()
    }

    @Test
    fun `getOptions should use only valid meals that are not null`() {
        val inValidMeals = listOf(
            createMealHelper(
                "Breakfast Pizza", 1, 100, LocalDate.parse("2022-01-01"), listOf("breakfast"),
                Nutrition(173.4, 18.0, 0.0, 17.0, 22.0, 35.0, 1.0), 3,
                listOf("Preheat", "Add toppings", "Bake"), "Quick pizza",
                listOf(), 3, 30
            ),

            createMealHelper(
                null, 2, 101, LocalDate.parse("2022-01-02"), listOf("dinner"),
                Nutrition(400.0, 25.0, 2.0, 30.0, 35.0, 5.0, 1.5), 4,
                listOf("Cook pasta", "Grill chicken", "null", "Mix together"), "Creamy pasta",
                listOf("Chicken", "Cream", "Parmesan", "Garlic", "Pasta", "Pepper"), 4, 45
            ),

            createMealHelper(
                "Beef Tacos", null, 102, LocalDate.parse("2022-01-03"), listOf("mexican"),
                Nutrition(300.0, 20.0, 5.0, 20.0, 25.0, 4.0, 1.2), 3,
                listOf("Cook beef", "Prepare shells", "Assemble tacos"), "Spicy beef tacos",
                listOf("Beef", "Taco shells", "Lettuce", "Cheddar", "Tomato", "Onion"), 3, 25
            ),

            createMealHelper(
                "Veggie Stir Fry",
                4,
                103,
                LocalDate.parse("2022-01-04"),
                listOf("vegetarian"),
                Nutrition(250.0, 10.0, 0.0, 15.0, 30.0, 10.0, 1.3),
                3,
                listOf("Chop veggies", "Stir fry", "Serve hot"),
                "Colorful and quick veggie stir fry",
                listOf("Broccoli", "Carrot", "Bell Pepper", "Soy Sauce", "Garlic", "Ginger"),
                3,
                20
            )
        )
        every { mealsProvider.getMeals() } returns inValidMeals
        ingredientGameUseCase = IngredientGameUseCase(mealsProvider)
        val options = ingredientGameUseCase.getOptions()

        val result = ingredientGameUseCase.getMealName(options)
        assertEquals("Veggie Stir Fry", result)
    }


    @Test
    fun `getQuestionNumber should increase with correct answers`() {
        every { mealsProvider.getMeals() } returns meals

        assertEquals(1, ingredientGameUseCase.getQuestionNumber())
        ingredientGameUseCase.execute(fixedOptions, 1)
        assertEquals(2, ingredientGameUseCase.getQuestionNumber())
    }

    @Test
    fun `getNextMeal should return the next unused meal`() {
        every { mealsProvider.getMeals() } returns meals
        ingredientGameUseCase = IngredientGameUseCase(mealsProvider)

        val result = ingredientGameUseCase.getMealName(ingredientGameUseCase.getOptions())

        assertThat(result).isEqualTo("Breakfast Pizza")
    }

    @Test
    fun `getMealName should return the correct meal name`() {
        val result = ingredientGameUseCase.getMealName(fixedOptions)
        assertThat(result).isEqualTo("Pizza")
    }

    @Test
    fun `getMealName should return null string when options is null`() {
        val result = ingredientGameUseCase.getMealName(null)
        assertEquals("null", result)
    }

    @Test
    fun `getPoints should return 1000`() {
        val result = ingredientGameUseCase.getPoints()
        assertThat(result).isEqualTo(1000)
    }

    @Test
    fun `getOptions should not fail when some meals have null ingredients`() {
        every { mealsProvider.getMeals() } returns meals
        ingredientGameUseCase = IngredientGameUseCase(mealsProvider)
        // When
        val question = ingredientGameUseCase.getOptions()

        // Then: Assert the question is generated and contains correct data
        assertThat(question).isNotNull()
        assertThat(question?.options?.size).isEqualTo(3)
    }

    @Test
    fun `getOptions should ignore meals with null or empty ingredients`() {
        every { mealsProvider.getMeals() } returns meals
        ingredientGameUseCase = IngredientGameUseCase(mealsProvider)
        val question = ingredientGameUseCase.getOptions()

        assertThat(question).isNotNull()
        assertThat(question?.options).hasSize(3)
        assertThat(question?.options).doesNotContain(null)
        assertThat(question?.correctIngredient).isIn(question?.options)
    }

    @Test
    fun `only valid meals are filtered and used to generate questions`() {
        every { mealsProvider.getMeals() } returns meals
        ingredientGameUseCase = IngredientGameUseCase(mealsProvider)

        val generatedMeals = mutableSetOf<String>()
        repeat(3) {
            val question = ingredientGameUseCase.getOptions()
            question?.let { generatedMeals.add(it.mealName) }
        }

        val expectedValidMeals = listOf("Breakfast Pizza", "Chicken Alfredo", "Beef Tacos")

        assertThat(generatedMeals).containsExactlyElementsIn(expectedValidMeals)
    }

}