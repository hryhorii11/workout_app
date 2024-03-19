package com.example.dyplom.data

sealed class UIState<T> {
    class Idle<T> : UIState<T>()
    class Loading<T> : UIState<T>()
    class Error<T>(val error: String) : UIState<T>()
    class Success<T>(val data: T) : UIState<T>()
}

sealed class ValidationResult {
    data object Valid : ValidationResult()
    class Invalid(val errorMessage: String) : ValidationResult()
}