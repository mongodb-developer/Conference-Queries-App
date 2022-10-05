package com.mongodb.dublinmug_kmm

import CommonFlow
import asCommonFlow
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.ext.query
import io.realm.kotlin.log.LogLevel
import io.realm.kotlin.mongodb.App
import io.realm.kotlin.mongodb.Credentials
import io.realm.kotlin.mongodb.User
import io.realm.kotlin.mongodb.subscriptions
import io.realm.kotlin.mongodb.sync.SyncConfiguration
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RealmRepo {

    private lateinit var realmOnDevice: Realm

    private val realmApp by lazy { App.create("application-0-elgah") }

    private val realm by lazy {
        val user = realmApp.currentUser!!
        val config = SyncConfiguration
            .Builder(user, setOf(QueryInfo::class))
            .log(LogLevel.ALL)
            .initialSubscriptions { realm ->
                realm.query<QueryInfo>()
            }
            .build()
        Realm.open(config)
    }

    init {
        realmOnDeviceSetup()
    }

    private fun realmOnDeviceSetup() {
        val schemaClass = setOf(QueryInfo::class)

        val config = RealmConfiguration
            .Builder(schema = schemaClass)
            .name("DubMug")
            .schemaVersion(1)
            .log(LogLevel.ALL)
            .build()

        realmOnDevice = Realm.open(config)
    }

    suspend fun saveInfo(query: String) {
        realmApp.login(Credentials.anonymous())

        realm.subscriptions.update {
            this.add(
                query = realm.query<QueryInfo>(),
                name = "subscription name",
                updateExisting = true
            )
        }

        val info = QueryInfo().apply {
            _id = RandomUUID().randomId
            queries = query
        }

        realm.write {
            copyToRealm(info)
        }
    }

    fun getAllData(): CommonFlow<List<String>> {
        return realm.query<QueryInfo>().asFlow().map {
            it.list.map { it.queries }
        }.asCommonFlow()
    }
}
