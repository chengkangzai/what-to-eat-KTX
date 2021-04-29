package com.example.whattoeatktx.model

import com.google.firebase.Timestamp

class Food(
    id: String,
    food: String,
    userID: String,
    timestamp: Timestamp,
    tags: List<String>
) {

    private val id: String = "";
    private val food: String = "";
    private val userID: String = "";

    override fun toString(): String {
        return this.id + ", " + this.food + ", " + this.userID
    }
}