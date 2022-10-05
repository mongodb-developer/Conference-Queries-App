package com.mongodb.dublinmug_kmm

import java.util.*

actual class RandomUUID {

    actual val randomId: String
        get() = UUID.randomUUID().toString()
}