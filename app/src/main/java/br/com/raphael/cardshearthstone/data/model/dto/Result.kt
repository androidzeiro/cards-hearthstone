package br.com.raphael.cardshearthstone.data.model.dto

sealed class Result<out T> {
    data class Success<out T>(val value: T, val statusCode: Int) : Result<T>()
    data class Error(val message: String, val statusCode: Int) : Result<Nothing>()
    data class NetworkError(val message: String) : Result<Nothing>()
}