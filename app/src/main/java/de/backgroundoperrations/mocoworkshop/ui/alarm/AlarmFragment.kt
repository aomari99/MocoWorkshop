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
        val startbtn = root.findViewById<Button>(R.id.startalarm)   //get start button
        val time = root.findViewById<TimePicker>(R.id.timePicker1)  // get TimePicker
        time.setIs24HourView(true); // set timePicker mode to 24 hour
        startbtn.setOnClickListener {   //start button clicked

            //creates a Toast which shows The Selected Minute and Hour
            Toast.makeText(
                root.context,
                "Der Alarm kommt um " + time.hour.toString() + ":" + getDD(time.minute),
                Toast.LENGTH_LONG
            ).show()

            //creates a Toast which shows The Difference in Seconds From when the Selected Time to now
            Toast.makeText(
                root.context,
                "Der Alarm kommt in ${times(time.hour, time.minute) /1000}  sekuden ",
                Toast.LENGTH_LONG
            ).show()


            val intent = Intent(root.context, Alarm::class.java)    //create an intent for the BroadcastReceiver Class
            val pendingIntent = PendingIntent.getBroadcast(         //create a Pending Intent (Broadcast Intent ) so the AlarmManager can execute my BroadcastReceiver
                root.context.applicationContext, 234, intent, 0
            )
            val alarmManager = root.context.getSystemService(Context.ALARM_SERVICE) as AlarmManager // get the System AlarmManager
            alarmManager[AlarmManager.RTC_WAKEUP, System.currentTimeMillis() +  times(
                time.hour,
                time.minute
            ) ] = pendingIntent     // Create an Alarm of Type RTC_WAKEUP and get current time in milli seconds and add in how many milli seconds it should start the pending intent


        }
        return root
    }

    fun times(hour: Int, minute: Int): Long {   // get time difference From now to the set clock time
        var format = SimpleDateFormat("HH:mm:ss") //set the time format
        val t1 = format.parse("${hour}:${getDD(minute)}:00")    // format time
        val currentTime: String = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())  // get current time as String

        val t2 = format.parse(currentTime) //format current time


        return abs(t1.time - t2.time)   //return time difference
    }

    fun getDD(num: Int): String? {  // funtion format the time with a zero if the number has just one digit else just return num
        return if (num > 9) "" + num else "0$num"
    }
}