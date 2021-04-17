package com.example.whattoeatktx.service

import com.example.whattoeatktx.R

class VersionService {
    val version = "0.0.1"

    fun about(): String {
        return "${R.string.app_name} v ${this.version}. \n " +
                "Build by ${R.string.author} (https://github.com/chengkangzai)"
    }


}