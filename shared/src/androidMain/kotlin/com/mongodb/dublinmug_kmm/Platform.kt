package com.mongodb.dublinmug_kmm

actual class Platform {
    actual val getPlatform: String
        get() = "Android ${android.os.Build.VERSION.SDK_INT}"
}
