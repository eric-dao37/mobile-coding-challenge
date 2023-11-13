package com.example.core.domain

sealed class DataState<out T> {
    data class Data<out T>(
        val data: T,
        val progressBarState: ProgressBarState = ProgressBarState.Idle
    ): DataState<T>()
    data class Error<out T>(
        val message: String,
        val progressBarState: ProgressBarState = ProgressBarState.Idle
    ): DataState<T>()
    data class Loading<T>(
        val progressBarState: ProgressBarState = ProgressBarState.Idle
    ): DataState<T>()
}