package com.example.whattoeatktx.ui.food

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.annotation.GlideModule
import com.example.whattoeatktx.R
import com.example.whattoeatktx.model.Food
import com.example.whattoeatktx.service.FirebaseHelper
import com.example.whattoeatktx.service.FirebaseIDGenerator
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.SetOptions
import com.google.firebase.ktx.Firebase

//https://code.luasoftware.com/tutorials/google-cloud-firestore/understanding-date-in-firestore/
//fun Timestamp.toLocalDateTime(zone: ZoneId = ZoneId.systemDefault()): LocalDateTime =
//    LocalDateTime.ofInstant(Instant.ofEpochMilli(seconds * 1000 + nanoseconds / 1000000), zone)
//
//fun LocalDateTime.toTimestamp() = Timestamp(atZone(ZoneId.systemDefault()).toEpochSecond(), nano)

@GlideModule
class FoodFragment : Fragment() {

    private lateinit var root: View

    private var s1: MutableList<String> = mutableListOf()
    private val uid: String = Firebase.auth.currentUser!!.uid
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val docRef = db.collection("user/${uid}/food")
    private var foods: MutableList<Food> = mutableListOf()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.root = inflater.inflate(R.layout.fragment_food, container, false)

        this.fetch()

        this.root.findViewById<Button>(R.id.btn_food_add).setOnClickListener { this.addFood() }
        this.root.findViewById<Button>(R.id.btnFindIt2).setOnClickListener { this.findFood() }
        return root
    }

    private fun findFood() {
        val foodText = root.findViewById<TextView>(R.id.txt_food)
        var run = true
        while (run) {
            foodText.text = foods[(0..this.foods.size).random() - 1].food
            Thread.sleep(50)
            run = (0..100).random() == 1
        }

        AlertDialog.Builder(root.context)
            .setTitle(getString(R.string.here_is_what_you_should_eat))
            .setMessage("${foodText.text} \uD83C\uDF89\uD83C\uDF89\uD83C\uDF89")
            .setPositiveButton(getString(R.string.k_thanks)) { di, _ -> di.dismiss() }
            .create()
            .show()


    }

    private fun addFood() {
        val foodName = root.findViewById<EditText>(R.id.input_food).text.toString()

        if (foodName.isEmpty()) {
            return AlertDialog.Builder(root.context)
                .setTitle(getString(R.string.validation_failed))
                .setMessage(getString(R.string.Please_enter_Food_that_you_wanted_to_add))
                .setPositiveButton(getString(R.string.OK)) { di, _ -> di.dismiss() }
                .create()
                .show()
        }

        val uniqueId = FirebaseIDGenerator.generateId()
        docRef.document(uniqueId).set(
            mapOf(
                "id" to uniqueId,
                "food" to foodName,
                "userID" to uid,
                "timestamp" to FirebaseHelper().getTimestamp(),
            ), SetOptions.merge()
        ).addOnCompleteListener {
            Toast.makeText(root.context, it.toString(), Toast.LENGTH_LONG).show()

        }
        this.fetch()
    }


    private fun fetch() {
        docRef.get()
            .addOnSuccessListener { documents ->
                this.s1 = mutableListOf()
                this.foods = mutableListOf()
                for (document in documents) {
//                    Log.d("DEBUGGGG", "${document.data["food"]} => ${document.data["timestamp"]}")
                    s1.add(document.data["food"] as String)
                    setupFoods(document)
                }
                this.setUpRecyclerView()

            }.addOnFailureListener {
                Toast.makeText(root.context, it.localizedMessage, Toast.LENGTH_LONG).show()
            }
    }

    private fun setupFoods(document: QueryDocumentSnapshot) {
        foods.add(
            Food(
                document.id,
                document.data["food"] as String,
                document.data["userID"] as? String,
                document.data["timestamp"] as? HashMap<*, *>,
                listOf(document.data["tags"]) as? List<String>
            )
        )
    }

    /**
     * https://www.youtube.com/watch?v=18VcnYN5_LM
     */
    @SuppressLint("SetTextI18n")
    private fun setUpRecyclerView() {
        val recyclerView = root.findViewById<RecyclerView>(R.id.list_foods)
        val foodAdapter = FoodAdapter(root.context, s1.take(3).toTypedArray(), foods)
        recyclerView.adapter = foodAdapter
        recyclerView.layoutManager = LinearLayoutManager(root.context)

//        ---------------------------------------
        root.findViewById<TextView>(R.id.food_food_title).text = "Your Food (${s1.size})"
    }

}