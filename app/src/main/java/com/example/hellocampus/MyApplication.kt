package com.example.hellocampus

import android.app.Application
import cn.leancloud.AVOSCloud
import cn.leancloud.AVObject




class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // 初始化learn cloud
        AVOSCloud.initialize(
            this, "0iVGWvU1iCWSgkWMACcscDLS-9Nh9j0Va",
            "zmNhU1Kqdh8434BwCrMm9nNv",
            "https://0ivgwvu1.lc-cn-e1-shared.com"
        )
    }
}