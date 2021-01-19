package de.backgroundoperrations.mocoworkshop.ui.myservices

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyServicesViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "This is Service Fragment"
    }
    val text: LiveData<String> = _text
}