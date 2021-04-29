package com.example.whattoeatktx.service

import android.util.Log
import com.example.whattoeatktx.model.Food
import com.google.firebase.Timestamp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class FoodService {
    private val uid: String? = Firebase.auth.currentUser.uid
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    init {
        Log.d("DEBUGGGG", "Food Service is initialized")
    }

    fun getFood(): MutableList<Food> {
        val foods = mutableListOf<Food>()
        val docRef = db
            .collection("user")
            .document(uid!!)
            .collection("food")


        docRef.get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    Log.d("DEBUGGGG", "${document.id} => ${document.data}")
                    foods.add(
                        Food(
                            document.id,
                            document.data["food"] as String,
                            document.data["userID"] as String,
                            document.data["timestamp"] as Timestamp,
                            listOf(document.data["tags"]) as List<String>
                        )
                    )
                }
            }.addOnFailureListener { it ->
                Log.e("ERROR", it.toString())
            }

        for (food in foods) {
            Log.d("Food Info", "$food")
        }
        return foods
    }

    fun update(oriFood: Food, newFood: Food) {
        TODO()
    }

    fun delete(food: Food) {
        TODO()
    }

    fun add(food: Food) {
        TODO()
    }
}

