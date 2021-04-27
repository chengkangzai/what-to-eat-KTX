package com.example.whattoeatktx.ui.tags

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
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
        val textView: TextView = root.findViewById(R.id.text_home)
        tagsViewModel.text.observe(viewLifecycleOwner, {
            textView.text = it
        })

//        FoodService().getFood()
//        root.findViewById<TextView>(R.id.TxtWelcome).text =
        root.findViewById<Button>(R.id.btnTEST).setOnClickListener {
//            Toast.makeText(this.context, result, Toast.LENGTH_LONG).show()
        }
        return root
    }
}