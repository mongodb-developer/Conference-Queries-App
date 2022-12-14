package com.mongodb.dublinmug_kmm

import CommonFlow
import asCommonFlow
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.log.LogLevel
import io.realm.kotlin.mongodb.*
import io.realm.kotlin.mongodb.sync.SyncConfiguration
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class RealmRepo {

    lateinit var realm: Realm

    private val appServiceInstance by lazy {
        // If logs are on app level then it set for everything ..
        val configuration =
            AppConfiguration.Builder("application-0-elgah").log(LogLevel.ALL).build()
        App.create(configuration)
    }

    private suspend fun setupRealmSync() {
        val user = appServiceInstance.login(Credentials.anonymous())
        val config = SyncConfiguration
            .Builder(user, setOf(QueryInfo::class))
            .initialSubscriptions { realm ->
                // only can write data, which cover in initialSubscriptions
                add(
                    query = realm.query<QueryInfo>(),
                    name = "subscription name",
                    updateExisting = true
                )
            }
            .build()
        realm = Realm.open(config)
    }

    suspend fun saveInfo(query: String) {
        if (!this::realm.isInitialized) {
            setupRealmSync()
        }

        val info = QueryInfo().apply {
            _id = RandomUUID().randomId
            queries = query
        }
        realm.write {
            copyToRealm(info)
        }
    }

    suspend fun getAllData(): CommonFlow<List<String>> {
        if (!this::realm.isInitialized) {
            setupRealmSync()
        }
        return realm.query<QueryInfo>().asFlow().map {
            it.list.map { it.queries }
        }.asCommonFlow()
    }
}
