package org.example.utils

class EmptyMealName(message : String)  : Exception(message)
class EmptyMeals(message : String)  : Exception(message)
class NoElementMatch(message : String)  : NoSuchElementException(message)
class InvalidDateFormatException(message: String) : Exception(message)
class NoMealsFoundException(message: String) : Exception(message)
