package com.example.whattoeatktx.service

import android.util.Log
import com.example.whattoeatktx.model.Food
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class FoodService {
    val uid = FirebaseAuth.getInstance().uid
    val db = FirebaseFirestore.getInstance()

    fun getFood() {
        val foods = mutableListOf<Food>()
        val docRef = db.collection("user").document(uid!!).collection("food")
        docRef.get().addOnSuccessListener { documents ->
            for (document in documents) {
                Log.d("Info", "${document.id} => ${document.data["timestamp"]}")
                foods.add(
                    Food(
                        document.id,
                        document.data["food"] as String,
                        document.data["userID"] as String,
                        Timestamp.now(),
//                        Timestamp(document.data["timestamp"][],),
                        listOf("ss")
                    )
                )
            }
        }

        for (food in foods) {
            Log.d("Food Info", "$food")
        }

    }
}

