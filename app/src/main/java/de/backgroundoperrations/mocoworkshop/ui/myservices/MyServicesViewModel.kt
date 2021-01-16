package de.backgroundoperrations.mocoworkshop.ui.myservices

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyServicesViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "This is Thread Fragment"
    }
    val text: LiveData<String> = _text
}