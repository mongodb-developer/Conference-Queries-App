package com.mongodb.dublinmug_kmm.android

import androidx.lifecycle.*
import com.mongodb.dublinmug_kmm.RealmRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val repo = RealmRepo()
    val queries: LiveData<List<String>> = liveData {
        emitSource(repo.getAllData().flowOn(Dispatchers.IO).asLiveData(Dispatchers.Main))
    }

    fun saveQuery(query: String) {
        viewModelScope.launch {
            repo.saveInfo(query)
        }
    }
}