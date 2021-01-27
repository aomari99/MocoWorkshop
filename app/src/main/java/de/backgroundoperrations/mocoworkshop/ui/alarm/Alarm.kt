package de.backgroundoperrations.mocoworkshop.ui.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.widget.Toast
import de.backgroundoperrations.mocoworkshop.R

class Alarm :BroadcastReceiver() //create a Class as a BroadcastReceiver
 {

    override fun onReceive(context: Context?, intent: Intent?) {  // get called when BroadcastReceiver Receives an Intent Broadcast
        val mp = MediaPlayer.create(context, R.raw.beep)  //create a MediaPlayer with a sound
        mp.start()  //play sound
        Toast.makeText(context,"Alarm",Toast.LENGTH_LONG).show() //show Toast with Message
    }
}