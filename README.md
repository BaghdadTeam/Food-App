# 🍔 Food Change Mood

Welcome to **Food Change Mood**, a Kotlin-based console application designed to help users explore, search, and interact with a large dataset of meals from around the world. Whether you’re a fitness enthusiast, a foodie, or someone with dietary restrictions, this app provides fun and functional features to discover meals tailored to your preferences and mood.

---

## 📁 Dataset Overview

The application uses a CSV file named `food.csv`, which contains structured data about various meals. Key highlights:

- **Nutrition column** contains an array in the following order:  
  `Calories, Total Fat, Sugar, Sodium, Protein, Saturated Fat, Carbohydrates`
- Some meals may have **null descriptions** (about 2%).
- Each meal includes metadata like name, description, country, tags, ingredients, preparation time, and nutrition.

---

## 🧩 Features

### 1. 🥗 Healthy Fast Food Filter
Filter and display meals that:
- Take **15 minutes or less** to prepare.
- Have **very low fat**, **saturated fat**, and **carbohydrates** compared to other meals.

---

### 2. 🔍 Smart Meal Search
Search for meals using **partial or typo-tolerant keywords**.  
Uses the **Knuth-Morris-Pratt (KMP)** algorithm for fast and efficient pattern matching.

---

### 3. 🇮🇶 Iraqi Meal Finder
Identify meals that are:
- Tagged with `"iraqi"` or  
- Contain the word `"Iraq"` in their description.

---

### 4. 🎲 Easy Food Suggestions
Suggest **10 random easy meals** that meet:
- Prep time ≤ 30 minutes  
- ≤ 5 ingredients  
- ≤ 6 preparation steps

---

### 5. 🎯 Guess Game: Prep Time Challenge
Let the user:
- Guess the preparation time of a random meal  
- Get hints (too high / too low)  
- Have **3 attempts** before revealing the correct answer

---

### 6. 🍭 Egg-Free Sweets
For users allergic to eggs:
- Randomly suggest **egg-free sweet** meals
- Users can like (view details) or dislike (get another suggestion)
- No repeated suggestions

---

### 7. 🥩 Keto Diet Helper
Suggest **keto-friendly meals** based on:
- **High fat**
- **Moderate protein**
- **Very low carbohydrates**

Uses a similar interaction as egg-free sweets (like/dislike, no repetition).

---

### 8. 📅 Search by Add Date
Let users:
- Input a date (format: `yyyy-MM-dd`)
- Get meals added on that date
- View details by entering the meal ID

Handles exceptions:
- `InvalidDateFormatException`
- `NoMealsFoundOnDateException`

---

### 9. 💪 Gym Helper
Input desired:
- Calories
- Protein

Get meals that **match or approximate** the desired nutritional values.

---

### 10. 🌍 Explore World Cuisines
Input a country name and explore up to **20 randomly ordered meals** related to that country across all columns.

---

### 11. 🎮 Ingredient Game
Guess the correct ingredient from 3 options for a given meal:
- Correct: +1000 points  
- Incorrect: Game Over  
- Max 15 correct answers

---

### 12. 🥔 I Love Potato
Show a **random list of 10 meals** that include **potatoes** in the ingredients.

---

### 13. ⚡ So Thin Problem
Suggest a random **high-calorie meal** (>700 calories) using the same like/dislike mechanism as other suggestion features.

---

### 14. 🐟 Seafood Protein Rankings
List all **seafood meals**, sorted by **protein content** from highest to lowest.  
Each result includes:
- Rank
- Meal name
- Protein amount

---

### 15. 🍝 Italian Group Meals
For a **large group traveling to Italy**, suggest **original Italian meals** tagged `"for-large-groups"` and `"italian"`.

---

## 🛠️ Tech Stack

- **Kotlin**
- **CSV parsing** with built-in and custom logic
- **Date handling** via Kotlin
- **KMP algorithm** for string matching
- **Functional programming style** for filtering and mapping datasets
- CLI-based interactivity

---

## 📦 Project Structure
```
FoodChangeMood/src
│
├── data/
│   ├── ColumnIndex.kt             # Object contains all CSV column indices
│   ├── CsvMealsRepository.kt      # CSV-based repository implementation
│   ├── DefaultMealsProvider.kt    # Default provider for meals data
│   └── RecordParser.kt            # Parses raw CSV records into entities
│
├── model/
│   ├── Meal.kt                    # Data class for meal entity
│   ├── IngredientQuestion.kt      # Represents ingredient-based questions
│   └── Nutrition.kt               # Nutrition data model
│
├── utils/
│   ├── CsvParser.kt               # CSV parsing logic (merged CsvReader/RecordParser)
│   ├── NutritionUtils.kt          # Helpers for nutrition calculations
│   └── KMPMatcher.kt              # String search logic (renamed from KMP.kt)
│
├── features/
│   ├── HealthyMeals.kt            # Healthy meal recommendations
│   ├── SearchMeals.kt             # Meal search functionality
│   ├── IraqiMeals.kt              # Iraqi-specific meal features
│   └── ...                        # Other feature implementations
│
├── exceptions/
│   ├── InvalidDateFormatException.kt
│   └── NoMealsFoundOnDateException.kt
│
├── di/                           # Dependency Injection (optional - not in target)
│   ├── AppModule.kt
│   ├── FeatureModule.kt
│   └── UseCaseModule.kt
│
├── Main.kt                       # CLI entry point & UI interactions
└── food.csv                      # CSV file that contains all meals data
```
---

## 🚀 Getting Started

### Prerequisites
- JDK 11+
- Kotlin Compiler
- A terminal or IDE like IntelliJ

### Running the App
```bash
# Compile
kotlinc Main.kt -include-runtime -d foodMoodApp.jar

# Run
java -jar foodMoodApp.jar
```

---


## 📧 Feedback & Contributions
Feel free to fork the project and submit a pull request or reach out with ideas, improvements, or issues. Your contributions are welcome!

---

## 🧠 Next Steps
- Add unit tests for all core features
- Support saving user preferences

---

## 🌟 Acknowledgment
This project is part of **The Chance** program—Week 2-3 Challenge.

---