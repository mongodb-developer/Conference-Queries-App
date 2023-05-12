package com.mongodb.dublinmug_kmm.android

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.mongodb.dublinmug_kmm.QueryInfo
import com.mongodb.dublinmug_kmm.RealmRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val repo = RealmRepo()
    val queries: LiveData<List<QueryInfo>> = liveData {
        emitSource(repo.getAllData().flowOn(Dispatchers.IO).asLiveData(Dispatchers.Main))
    }

    fun onSendClick(query: QueryInfo) {
        viewModelScope.launch {
            if (query._id.isBlank()) {
                repo.saveInfo(query.queries)
            } else {
                repo.editInfo(query)
            }
        }
    }
}