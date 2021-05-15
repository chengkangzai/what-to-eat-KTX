package com.app.whattoeatktx.ui.tags

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.bumptech.glide.annotation.GlideModule
import com.app.whattoeatktx.MyBaseActivity
import com.app.whattoeatktx.R
import com.app.whattoeatktx.service.FirebaseHelper
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.ktx.Firebase

@GlideModule
class ViewTagActivity : MyBaseActivity() {

    private lateinit var tagName: String
    private lateinit var tagId: String
    private lateinit var imageSrc: String

    private lateinit var imageView: ImageView
    private lateinit var tagNameTextView: TextView


    private val uid: String = Firebase.auth.currentUser!!.uid
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val docRef = db.collection("user/${uid}/tags")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_tag)
        this.imageView = findViewById(R.id.viewTag_img)
        this.tagNameTextView = findViewById(R.id.viewTag_tagName)

        this.getData()
        this.setData()

        this.findViewById<Button>(R.id.viewTag_edit).setOnClickListener { this.editTag() }
        this.findViewById<Button>(R.id.viewTag_delete).setOnClickListener { this.deleteTag() }
    }

    private fun deleteTag() {
        AlertDialog.Builder(this)
            .setTitle(String.format(getString(R.string.delete_), this.tagName))
            .setMessage(getString(R.string.are_you_sure))
            .setPositiveButton(getString(R.string.YES)) { _, _ ->
                this.docRef.document(this.tagId).delete()
                super.goToMain()
            }
            .setNegativeButton(getString(R.string.cancel)) { dialog, _ -> dialog.cancel() }
            .show()
    }

    private fun editTag() {
        var holder: String

        val input = EditText(this)
        input.background = null
        input.setText(this.tagName)

        val textInputLayout = TextInputLayout(this)
        textInputLayout.setPadding(25, 0, 25, 0)
        textInputLayout.addView(input)

        AlertDialog.Builder(this)
            .setTitle(String.format(getString(R.string.edit_food), this.tagName))
            .setView(textInputLayout)
            .setMessage(getString(R.string.please_enter_new_food_name))
            .setPositiveButton(getString(R.string.OK)) { _, _ ->
                holder = input.text.toString()
                this.docRef.document(this.tagId).set(
                    hashMapOf(
                        "name" to holder,
                        "timestamp" to FirebaseHelper().getTimestamp()
                    ),
                    SetOptions.merge()
                )
                super.goToMain()
            }
            .setNegativeButton(getString(R.string.cancel)) { dialog, _ -> dialog.cancel() }
            .show()
    }

    private fun getData() {
        if (intent.hasExtra("tagName") && intent.hasExtra("tagId") && intent.hasExtra("imageSrc")) {
            this.tagName = intent.extras!!.getString("tagName").toString()
            this.tagId = intent.extras!!.getString("tagId").toString()
            this.imageSrc = intent.extras!!.getString("imageSrc").toString()
        } else {
            Toast.makeText(this, getString(R.string.no_extra_info), Toast.LENGTH_LONG).show()
        }
    }

    private fun setData() {
        this.tagNameTextView.text = this.tagName
        Glide.with(this).load(this.imageSrc).into(this.imageView)
    }
}