package helpers.game

import helpers.createMealHelper
import kotlinx.datetime.LocalDate
import model.Nutrition
import org.example.model.IngredientQuestion

object IngredientGameTestData {
    val meals = listOf(
        createMealHelper(
            name = "Breakfast Pizza", 1,
            ingredients = listOf("Cheese", "Tomato", "Lettuce", "null", "Onion", "Beef"),
        ),

        createMealHelper(
            name = "Chicken Alfredo", 2,
            ingredients = listOf("Chicken", "Cream", "Parmesan", "Garlic", "Pasta", "Pepper")
        ),

        createMealHelper(
            "Beef Tacos", 3,
            ingredients = listOf("Beef", "Taco shells", "Lettuce", "Cheddar", "Tomato", "Onion")
        ),

        createMealHelper(
            "Veggie Stir Fry", 4,
            ingredients = listOf("Broccoli", "Carrot", "Bell Pepper", "Soy Sauce", "Garlic", "Ginger")
        ),

        createMealHelper(
            "Salmon Bowl", 5,
            ingredients = listOf("Salmon", "Rice", "Avocado", "Cucumber", "Soy Sauce", "Sesame"),
        ),

        createMealHelper(
            "Pancakes", 6,
            ingredients = listOf("Flour", "Egg", "Milk", "Sugar", "Baking Powder", "Butter")
        ),

        createMealHelper(
            "Lentil Soup", 7,
            ingredients = listOf("Lentils", "Carrot", "Celery", "Onion", "Garlic", "Tomato")
        ),

        createMealHelper(
            "Shrimp Scampi", 8,
            ingredients = listOf("Shrimp", "Garlic", "Butter", "Parsley", "Lemon", "Spaghetti")
        ),

        createMealHelper(
            "Caesar Salad", 9,
            ingredients = listOf("Lettuce", "Croutons", "Parmesan", "Caesar dressing", "Chicken")
        ),

        createMealHelper(
            "Steak and Potatoes", 10,
            ingredients = listOf("Steak", "Potatoes", "Salt", "Pepper", "Butter"),
        ),

        createMealHelper(
            "Fruit Smoothie", 11,
            ingredients = listOf("Banana", "Strawberry", "Yogurt", "Honey", "Milk")
        ),

        createMealHelper(
            "Baked Ziti", 12,
            ingredients = listOf("Ziti", "Tomato Sauce", "Mozzarella", "Ricotta", "Basil", "Onion")
        ),

        createMealHelper(
            "Quinoa Salad", 13,
            ingredients = listOf("Quinoa", "Tomato", "Cucumber", "Mint", "Olive oil", "Lemon juice")
        ),

        createMealHelper(
            "Eggplant Parmesan", 14,
            ingredients = listOf("Eggplant", "Parmesan", "Marinara", "Mozzarella", "Basil"),

            ),

        createMealHelper(
            "Sushi Rolls", 15,
            ingredients = listOf("Rice", "Seaweed", "Salmon", "Avocado", "Cucumber", "Soy Sauce")
        )
    )

    val inValidMeals = listOf(
        createMealHelper(
            "Breakfast Pizza", 1,
            ingredients = null
        ),

        createMealHelper(
            null, 2,
            ingredients = listOf("Chicken", "", "Parmesan", "Garlic", "Pasta", "Pepper")
        ),

        createMealHelper(
            "Beef Tacos", null,
            ingredients = listOf("Beef", "Taco shells", "Lettuce", "Cheddar", "Tomato", "Onion")
        ),

        createMealHelper(
            "Veggie Stir Fry", 4,
            ingredients = listOf("Broccoli", "Carrot", "Bell Pepper", "Soy Sauce", "Garlic", "Ginger"),
        )
    )

    val fixedOptions = IngredientQuestion(
        mealName = "Pizza",
        correctIngredient = "Cheese",
        options = listOf("Cheese", "lattice", "sugar")
    )

}