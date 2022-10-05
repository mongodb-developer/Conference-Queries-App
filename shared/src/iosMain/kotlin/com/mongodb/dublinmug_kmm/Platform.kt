package com.mongodb.dublinmug_kmm

import platform.UIKit.UIDevice
actual class Platform {
    actual val getPlatform: String
        get() = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion

}
