package com.example.whattoeatktx.ui.tags

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.whattoeatktx.R


class TagsFragment : Fragment() {

    private lateinit var tagsViewModel: TagsViewModel

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        tagsViewModel =
            ViewModelProvider(this).get(TagsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_tags, container, false)

        val listView: ListView = root.findViewById(R.id.list_tags) as ListView

        listView.setOnItemClickListener { _, _, _, _ ->

            super.getContext()?.let { AlertDialog.Builder(it) }
                ?.setMessage("TEST")
                ?.setPositiveButton("R.string.fire") { _, _ ->
                    Toast.makeText(this.context, "TEST", Toast.LENGTH_LONG).show()
                }
                ?.setNegativeButton("R.string.cancel") { _, _ ->
                    Toast.makeText(this.context, "TEST", Toast.LENGTH_LONG).show()
                }
                ?.create()
                ?.show()
        }

//        val currentUser = FirebaseAuth.getInstance().currentUser
//        root.findViewById<TextView>(R.id.TxtWelcome).text =
//            currentUser.email + " | " + currentUser.uid
//        root.findViewById<Button>(R.id.btnTEST).setOnClickListener {
//            FoodService().getFood()
//        }
//        root.findViewById<Button>(R.id.btnLogout).setOnClickListener {
//            FirebaseAuth.getInstance().signOut();
//            startActivity(Intent(this.context, LoginRegisterActivity::class.java))
//        }
        return root
    }

//    private fun ListView.setOnItemClickListener(tagsFragment: TagsFragment) {
//        val builder = AlertDialog.Builder(this.context)
//        builder.setMessage("TEST")
//            .setPositiveButton("R.string.fire")
//            { dialog, id ->
//                Toast.makeText(this.context, "TEST", Toast.LENGTH_LONG).show()
//            }
//            .setNegativeButton(
//                "R.string.cancel"
//            ) { dialog, id ->
//                Toast.makeText(this.context, "TEST", Toast.LENGTH_LONG).show()
//            }
//        // Create the AlertDialog object and return it
//        builder.create().show()
//    }
}

