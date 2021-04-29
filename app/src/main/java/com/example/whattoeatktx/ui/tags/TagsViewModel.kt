package com.example.whattoeatktx.ui.tags

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.whattoeatktx.R

class TagsViewModel : ViewModel() {

//    private val _text = MutableLiveData<String>().apply {
//        value = "This is Tags Fragment"
//    }
//    val text: LiveData<String> = _text

    private val _arrayString = listOf(R.array.sections.toString())

    val arrayString = _arrayString;
}