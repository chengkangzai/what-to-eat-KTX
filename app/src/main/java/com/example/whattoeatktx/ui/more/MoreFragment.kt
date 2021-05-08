package com.example.whattoeatktx.ui.more

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.whattoeatktx.PrivacyNoticeActivity
import com.example.whattoeatktx.R
import com.example.whattoeatktx.service.FirebaseIDGenerator
import com.example.whattoeatktx.service.VersionService
import com.example.whattoeatktx.ui.auth.LoginRegisterActivity
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.ktx.Firebase
import java.util.*

class MoreFragment : Fragment() {
    private lateinit var root: View

    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val uid: String = Firebase.auth.currentUser!!.uid
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val docRef = db.collection("feedback")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        this.root = inflater.inflate(R.layout.fragment_more, container, false)

        this.root.findViewById<Button>(R.id.morePage_btnLogout).setOnClickListener {
            auth.signOut()
            startActivity(Intent(root.context, LoginRegisterActivity::class.java))
        }
        this.root.findViewById<Button>(R.id.morePage_btnMakeFeedback)
            .setOnClickListener { this.handleFeedback() }
        this.root.findViewById<Button>(R.id.morePage_btnAbout)
            .setOnClickListener { this.showAbout() }
        this.root.findViewById<Button>(R.id.morePage_btnPrivacyNotice).setOnClickListener {
            startActivity(Intent(root.context, PrivacyNoticeActivity::class.java))
        }
        return this.root
    }

    private fun handleFeedback() {
        var holder: String

        val input = EditText(this.root.context)
        input.background = null

        val textInputLayout = TextInputLayout(this.root.context)
        textInputLayout.setPadding(25, 0, 25, 0)
        textInputLayout.addView(input)


        AlertDialog.Builder(this.root.context)
            .setTitle(getString(R.string.make_feedback))
            .setView(textInputLayout)
            .setMessage(getString(R.string.please_enter_your_feedback))
            .setPositiveButton(getString(R.string.OK)) { _, _ ->
                holder = input.text.toString()
                val id = FirebaseIDGenerator.generateId()
                docRef.document(id).set(
                    hashMapOf(
                        "feedback" to holder,
                        "id" to id,
                        "user" to uid,
                        "timestamp" to Date()
                    ),
                    SetOptions.merge()
                )
                Toast.makeText(root.context, getString(R.string.DONE), Toast.LENGTH_LONG).show()
            }
            .setNegativeButton(getString(R.string.cancel)) { dialog, _ -> dialog.cancel() }
            .show()
    }

    private fun showAbout() {
        android.app.AlertDialog.Builder(root.context)
            .setTitle(getString(R.string.about_this_app))
            .setPositiveButton(getString(R.string.OK)) { dialog, _ ->
                dialog.dismiss()
            }
            .setMessage(
                String.format(
                    resources.getString(R.string.about_string),
                    resources.getString(R.string.app_name),
                    VersionService().version,
                    resources.getString(R.string.author)
                )
            )
            .show()
    }
}