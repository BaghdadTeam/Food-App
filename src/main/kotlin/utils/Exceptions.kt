package org.example.utils

class EmptyMealNameException(message : String = "")  : Exception(message)
class EmptyMealsException(message : String = "")  : Exception(message)
class NoMealFoundException(message : String = "")  : NoSuchElementException(message)
class InvalidDateFormatException(message: String = "") : Exception(message)
class EmptyPatternException(message: String = "") : Exception(message)
class EmptyTextException(message: String = "") : Exception(message)