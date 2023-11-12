package com.example.core.domain

sealed class DataState<out T> {
    data class Data<out T>(val value: T): DataState<T>()
    data class Error<out T>(val message: String): DataState<T>()
    data class Loading<T>(
        val progressBarState: ProgressBarState = ProgressBarState.Idle
    ): DataState<T>()
}