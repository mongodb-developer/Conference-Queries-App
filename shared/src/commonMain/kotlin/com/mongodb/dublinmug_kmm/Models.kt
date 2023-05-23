package com.mongodb.dublinmug_kmm

import io.realm.kotlin.types.RealmInstant
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class QueryInfo : RealmObject {

    @PrimaryKey
    var _id: String = ""
    var queries: String = ""
    var timestamp: RealmInstant = RealmInstant.now()
}