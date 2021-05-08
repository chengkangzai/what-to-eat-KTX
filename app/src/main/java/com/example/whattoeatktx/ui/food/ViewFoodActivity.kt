package com.example.whattoeatktx.ui.food

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.bumptech.glide.annotation.GlideModule
import com.example.whattoeatktx.MyBaseActivity
import com.example.whattoeatktx.R
import com.example.whattoeatktx.service.FirebaseHelper
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.ktx.Firebase
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

@GlideModule
class ViewFoodActivity : MyBaseActivity() {

    private lateinit var foodName: String
    private lateinit var foodId: String
    private lateinit var timestamp: HashMap<*, *>
    private lateinit var imageSrc: String
    private var tags: ArrayList<String>? = null

    private lateinit var imageView: ImageView
    private lateinit var foodNameTextView: TextView
    private lateinit var timestampTextView: TextView

    private val uid: String = Firebase.auth.currentUser!!.uid
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val docRef = db.collection("user/${uid}/food")
    private val tagDocRef = db.collection("user/${uid}/tags")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_food)

        this.imageView = findViewById(R.id.viewFood_foodImage)
        this.foodNameTextView = findViewById(R.id.viewFood_txtFoodName)
        this.timestampTextView = findViewById(R.id.viewFood_txtTimestamp)

        this.getData()
        this.setData()
        this.setTags()

        findViewById<Button>(R.id.viewFood_btnEdit).setOnClickListener { this.editFood() }
        findViewById<Button>(R.id.viewFood_btnDelete).setOnClickListener { this.deleteFood() }
    }

    private fun setTags() {
        val ll_main = findViewById<LinearLayout>(R.id.ll_main_layout)
        ll_main.removeAllViews()

        val green = resources.getColor(R.color.green)
        val lightYellow = resources.getColor(R.color.light_yellow)
        val white = resources.getColor(R.color.white)
        val black = resources.getColor(R.color.black)
        tagDocRef
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val button = com.google.android.material.button.MaterialButton(this)
                    // setting layout_width and layout_height using layout parameters
                    button.layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    )
                    var hit = false
                    if (this.tags != null) {
                        for (tag in this.tags!!) {
                            val tagID = tag.split("user/${uid}/tags/")[1]
                            if (document.id == tagID) {
                                hit = true
                                break
                            }
                        }
                    }

                    if (hit) {
                        button.setBackgroundColor(green)
                        button.setTextColor(white)
                        button.setOnClickListener { this.detachTag(document.id) }
                    } else {
                        button.setBackgroundColor(lightYellow)
                        button.setTextColor(black)
                        button.setOnClickListener { this.attachTag(document.id) }
                    }

                    button.text = document.data["name"] as String
                    // add Button to LinearLayout
                    ll_main.addView(button)
                }
            }

    }

    private fun attachTag(tagID: String) {
        if (this.tags == null) {
            //create a list
            val temp = listOf("user/${uid}/tags/${tagID}")
            this.docRef.document(this.foodId)
                .set(
                    hashMapOf(
                        "tags" to temp
                    ), SetOptions.merge()
                )
        } else {
            //add a item
            this.tags!!.add("user/${uid}/tags/${tagID}")
            this.docRef.document(this.foodId)
                .set(
                    hashMapOf(
                        "tags" to this.tags
                    ), SetOptions.merge()
                )
        }
        this.setTags()
    }

    private fun detachTag(tagID: String) {
        this.tags!!.remove("user/${uid}/tags/${tagID}")
        this.docRef.document(this.foodId)
            .set(
                hashMapOf(
                    "tags" to this.tags
                ), SetOptions.merge()
            )
        this.setTags()
    }

    private fun editFood() {
        var holder: String

        val input = EditText(this)
        input.background = null
        input.setText(this.foodName)

        val textInputLayout = TextInputLayout(this)
        textInputLayout.setPadding(25, 0, 25, 0)
        textInputLayout.addView(input)

        AlertDialog.Builder(this)
            .setTitle(String.format(getString(R.string.edit_food), this.foodName))
            .setView(textInputLayout)
            .setMessage(getString(R.string.please_enter_new_food_name))
            .setPositiveButton(getString(R.string.OK)) { _, _ ->
                holder = input.text.toString()
                docRef.document(this.foodId).set(
                    hashMapOf(
                        "food" to holder,
                        "timestamp" to FirebaseHelper().getTimestamp()
                    ),
                    SetOptions.merge()
                )
                super.goToMain()
            }
            .setNegativeButton(getString(R.string.cancel)) { dialog, _ -> dialog.cancel() }
            .show()
    }

    private fun deleteFood() {
        AlertDialog.Builder(this)
            .setTitle(String.format(getString(R.string.delete_), this.foodName))
            .setMessage(getString(R.string.are_you_sure))
            .setPositiveButton(getString(R.string.YES)) { _, _ ->
                this.docRef.document(this.foodId).delete()
                super.goToMain()
            }
            .setNegativeButton(getString(R.string.cancel)) { dialog, _ -> dialog.cancel() }
            .show()
    }

    private fun getData() {
        if (intent.hasExtra("foodName") && intent.hasExtra("timestamp")
            && intent.hasExtra("imgSrc") && intent.hasExtra("foodId")
        ) {
            this.foodName = intent.extras!!.getString("foodName").toString()
            this.timestamp = intent.extras!!.get("timestamp") as HashMap<*, *>
            this.imageSrc = intent.extras!!.getString("imgSrc").toString()
            this.foodId = intent.extras!!.getString("foodId").toString()
            this.tags = intent.extras!!.getStringArrayList("tags")
        } else {
            Toast.makeText(this, getString(R.string.no_extra_info), Toast.LENGTH_LONG).show()
        }

    }


    @SuppressLint("SetTextI18n")
    private fun setData() {
        this.foodNameTextView.text = "${getString(R.string.food_)} ${this.foodName}"
        val odt =
            LocalDateTime.ofEpochSecond(
                this.timestamp["seconds"] as Long,
                0,
                ZoneOffset.ofHours(8)
            ).format(DateTimeFormatter.ofPattern("dd/MM/yy, hh:mm"))

        val aa = if (odt.split(":")[0] <= "12") "AM" else "PM"

        this.timestampTextView.text = String.format(getString(R.string.added_updated_on), odt, aa)
        Glide.with(this).load(this.imageSrc).into(this.imageView)
    }
}

