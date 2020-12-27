package de.backgroundoperrations.mocoworkshop.ui.alarm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AlarmViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Alarm Fragment"
    }
    val text: LiveData<String> = _text
}