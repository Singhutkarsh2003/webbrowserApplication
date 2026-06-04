package com.example.webbrowserapplication.data.repository

import com.example.webbrowserapplication.data.local.HistoryDao
import com.example.webbrowserapplication.data.local.HistoryEntity

class HistoryRepository(
    private val dao: HistoryDao
) {

    fun getAllHistory() = dao.getAllHistory()

    suspend fun  insertHistory(history: HistoryEntity){
        dao.insertHistory(history)
    }

    suspend fun  deleteHistory(history: HistoryEntity){
        dao.deleteHistory(history)
    }

}