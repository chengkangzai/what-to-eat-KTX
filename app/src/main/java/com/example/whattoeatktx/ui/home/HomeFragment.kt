package com.example.whattoeatktx.ui.home

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
import com.example.whattoeatktx.service.FoodService
import com.example.whattoeatktx.service.UserService

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
        homeViewModel.text.observe(viewLifecycleOwner, {
            textView.text = it
        })

        root.findViewById<TextView>(R.id.TxtWelcome).text =
            UserService().isLoggedIn().toString() + "Stupid "
        root.findViewById<Button>(R.id.btnTEST).setOnClickListener {
            FoodService().getFood()
//            Toast.makeText(this.context, result, Toast.LENGTH_LONG).show()
        }
        return root
    }
}