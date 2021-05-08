package com.example.whattoeatktx.ui.tags

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.annotation.GlideModule
import com.example.whattoeatktx.R
import com.example.whattoeatktx.model.Tag
import com.example.whattoeatktx.service.FirebaseHelper
import com.example.whattoeatktx.service.FirebaseIDGenerator
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.SetOptions
import com.google.firebase.ktx.Firebase

@GlideModule
class TagsFragment : Fragment() {

    private lateinit var root: View

    private var s1: MutableList<String> = mutableListOf()

    private val uid: String = Firebase.auth.currentUser!!.uid
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val docRef = db.collection("user/${uid}/tags")
    private var tags: MutableList<Tag> = mutableListOf()

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.root = inflater.inflate(R.layout.fragment_tags, container, false)
        this.fetch()

        root.findViewById<Button>(R.id.tags_addBtn).setOnClickListener { this.addTags() }
        return this.root
    }

    private fun addTags() {
        val tagName = root.findViewById<EditText>(R.id.tags_input).text.toString()

        if (tagName.isEmpty()) {
            return AlertDialog.Builder(root.context)
                .setTitle(getString(R.string.validation_failed))
                .setMessage(getString(R.string.please_enter_the_tag_name_that_you_want_to_add))
                .setPositiveButton(getString(R.string.OK)) { di, _ -> di.dismiss() }
                .create()
                .show()
        }

        val uniqueId = FirebaseIDGenerator.generateId()
        docRef.document(uniqueId).set(
            mapOf(
                "id" to uniqueId,
                "name" to tagName,
                "timestamp" to FirebaseHelper().getTimestamp(),
            ), SetOptions.merge()
        ).addOnCompleteListener {
            Toast.makeText(root.context, getString(R.string.DONE), Toast.LENGTH_LONG).show()
        }
        root.findViewById<EditText>(R.id.tags_input).setText("")
        this.fetch()
    }

    private fun fetch() {
        docRef.get()
            .addOnSuccessListener { documents ->
                this.s1 = mutableListOf()
                this.tags = mutableListOf()
                for (document in documents) {
                    Log.d("DEBUGGGG", "${document.id} => ${document.data}")
                    s1.add(document.data["name"] as String)
                    setupTags(document)
                }
                this.setUpRecyclerView()
            }.addOnFailureListener {
                Toast.makeText(root.context, it.localizedMessage, Toast.LENGTH_LONG).show()
            }
    }

    private fun setUpRecyclerView() {
        val recyclerView = root.findViewById<RecyclerView>(R.id.tags_list)
        val tagAdapter = TagAdapter(root.context, s1.toTypedArray(), tags)
        recyclerView.adapter = tagAdapter
        recyclerView.layoutManager = LinearLayoutManager(root.context)
    }

    private fun setupTags(document: QueryDocumentSnapshot) {
        tags.add(
            Tag(
                document.id,
                document.data["name"] as? String,
                document.data["timestamp"] as HashMap<*, *>
            )
        )

    }

}

