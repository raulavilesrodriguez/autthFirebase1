package com.example.autenticar1.screens.stats

import androidx.compose.runtime.mutableStateOf
import com.example.autenticar1.model.service.LogService
import com.example.autenticar1.model.service.StorageService
import com.example.autenticar1.screens.AutenticarViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StatsViewModel @Inject constructor(
    logService: LogService,
    private val storageService: StorageService
) : AutenticarViewModel(logService) {
    val uiState = mutableStateOf(StatsUiState())

    init {
        launchCatching { loadStats() }
    }

    private suspend fun loadStats() {
        val updatedUiState = StatsUiState(
            completedTasksCount = storageService.getCompletedTasksCount(),
            importantCompletedTasksCount = storageService.getImportantCompletedTasksCount(),
            mediumHighTasksToCompleteCount = storageService.getMediumHighTasksToCompleteCount()
        )

        uiState.value = updatedUiState
    }
}