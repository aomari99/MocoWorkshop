package de.backgroundoperrations.mocoworkshop.ui.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TimePicker
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import de.backgroundoperrations.mocoworkshop.R
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs

class AlarmFragment : Fragment() {

    private lateinit var alarmViewModel: AlarmViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        alarmViewModel =
                ViewModelProvider(this).get(AlarmViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_alarm, container, false)

            return root
    }


}
