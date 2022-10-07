package com.mongodb.dublinmug_kmm

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.RealmUUID
import io.realm.kotlin.types.annotations.PrimaryKey

class QueryInfo : RealmObject {
    @PrimaryKey
    var _id: String = RealmUUID.random().toString()
    var queries: String = ""
}