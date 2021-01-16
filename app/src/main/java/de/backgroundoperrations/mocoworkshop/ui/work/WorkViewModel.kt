package de.backgroundoperrations.mocoworkshop.ui.work

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class WorkViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Work Fragment"
    }
    val text: LiveData<String> = _text
}