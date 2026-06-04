package com.example.webbrowserapplication.ui.history

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.webbrowserapplication.data.local.BrowserDatabase
import com.example.webbrowserapplication.data.local.HistoryEntity
import com.example.webbrowserapplication.data.repository.HistoryRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HistoryViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val repository: HistoryRepository

    val historyList: StateFlow<List<HistoryEntity>>

    init {

        val dao = BrowserDatabase
            .getDatabase(application)
            .historyDao()

        repository = HistoryRepository(dao)

        historyList = repository
            .getAllHistory()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )
    }

    fun deleteHistory(
        history: HistoryEntity
    ) {
        viewModelScope.launch {
            repository.deleteHistory(history)
        }
    }
}