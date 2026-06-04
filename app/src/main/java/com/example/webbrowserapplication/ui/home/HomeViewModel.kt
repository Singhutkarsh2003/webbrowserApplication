package com.example.webbrowserapplication.ui.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class HomeViewModel: ViewModel() {

    var url by mutableStateOf("")
        private  set
    fun updateUrl(value : String){
        url = value
    }
    fun clearUrl(){
        url = ""
    }

}