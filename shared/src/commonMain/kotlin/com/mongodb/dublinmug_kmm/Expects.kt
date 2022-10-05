package com.mongodb.dublinmug_kmm

expect class RandomUUID(){
    val randomId : String
}

expect class Platform(){
    val getPlatform : String
}