package com.example.whattoeatktx.model


class Food(
    val id: String,
    val food: String,
    val userID: String?,
    val timestamp: HashMap<*, *>?,
    val tags: ArrayList<String>?
)