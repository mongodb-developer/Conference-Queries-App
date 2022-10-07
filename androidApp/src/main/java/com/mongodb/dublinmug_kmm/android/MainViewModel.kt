package com.mongodb.dublinmug_kmm.android

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.mongodb.dublinmug_kmm.RealmRepo
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val repo = RealmRepo()
    val queries: LiveData<List<String>> = liveData {
        emitSource(repo.getAllData().asLiveData())
    }

    fun saveQuery(query: String) {
        viewModelScope.launch {
            repo.saveInfo(query)
        }
    }
}