package com.mongodb.dublinmug_kmm

import CommonFlow
import asCommonFlow
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.log.LogLevel
import io.realm.kotlin.mongodb.*
import io.realm.kotlin.mongodb.sync.SyncConfiguration
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class RealmRepo {

    lateinit var realm: Realm

    private val appServiceInstance by lazy {
        // If logs are on app level then it set for everything ..
        val configuration = AppConfiguration.Builder("application-0-elgah")
            .log(LogLevel.ALL)
            .build()
        App.create(configuration)
    }

    private suspend fun setupRealmSync() {
        val user = appServiceInstance.login(Credentials.anonymous())
        val config = SyncConfiguration
            .Builder(user, setOf(QueryInfo::class))
            .initialSubscriptions { realm ->
                // Only data covered by a subscription can be written to the realm.
                add(query = realm.query<QueryInfo>())
            }
            .build()
        realm = Realm.open(config)
    }

    suspend fun saveInfo(query: String) {
        return withContext(Dispatchers.Default) {
            if (!this@RealmRepo::realm.isInitialized) {
                setupRealmSync()
            }
            realm.write {
                copyToRealm(QueryInfo().apply {
                    queries = query
                })
            }
        }
    }

    suspend fun getAllData(): CommonFlow<List<String>> {
        return withContext(Dispatchers.Default) {
            if (!this@RealmRepo::realm.isInitialized) {
                setupRealmSync()
            }
            realm.query<QueryInfo>().asFlow().map {
                it.list.map { it.queries }
            }
        }.asCommonFlow()
    }
}
