package com.example.whattoeatktx.ui.tags

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.whattoeatktx.R


class TagsFragment : Fragment() {
    private lateinit var root: View
    private lateinit var tagsViewModel: TagsViewModel

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        tagsViewModel =
            ViewModelProvider(this).get(TagsViewModel::class.java)
        this.root = inflater.inflate(R.layout.fragment_tags, container, false)

//TODO -- Similar to Food
        return this.root
    }

}

