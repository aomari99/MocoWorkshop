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
        val startbtn = root.findViewById<Button>(R.id.startalarm)
        val time = root.findViewById<TimePicker>(R.id.timePicker1)
        time.setIs24HourView(true);
        startbtn.setOnClickListener {


            Toast.makeText(
                root.context,
                "Der Alarm kommt um " + time.hour.toString() + ":" + getDD(time.minute),
                Toast.LENGTH_LONG
            ).show()
            Toast.makeText(root.context, "Der Alarm kommt in ${times(time.hour,time.minute) }  sekuden " , Toast.LENGTH_LONG).show()


            val intent = Intent(root.context, Alarm::class.java)
            val pendingIntent = PendingIntent.getBroadcast(
                root.context.applicationContext, 234, intent, 0
            )
            val alarmManager = root.context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmManager[AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + times(
                time.hour,
                time.minute
            ) * 1000] = pendingIntent

     }
            return root
    }

    fun times (hour : Int, minute : Int) : Long{
        var format = SimpleDateFormat("HH:mm:ss")
        val t1 = format.parse("${hour}:${getDD(minute)}:00")
        val currentTime: String = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())

        val t2 = format.parse(currentTime)
        // Toast.makeText(this, "D  ${t1.toString()}  D2  ${t2.toString()}  " , Toast.LENGTH_LONG).show()

        var diff  = abs( t1.time -t2.time)

        val s =   (diff /1000)
        return  s
    }
    fun getDD(num: Int): String? {
        return if (num > 9) "" + num else "0$num"
    }
}
