package com.app.whattoeatktx.service

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class UserService {

    fun getUserInfo(): FirebaseUser? {
        return FirebaseAuth.getInstance().currentUser
    }

    fun isLoggedIn(): Boolean {
        return FirebaseAuth.getInstance().currentUser != null
    }

    fun isNotLoggedIn(): Boolean {
        return FirebaseAuth.getInstance().currentUser == null
    }
}