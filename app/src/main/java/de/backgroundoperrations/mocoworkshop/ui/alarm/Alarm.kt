package de.backgroundoperrations.mocoworkshop.ui.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer

import android.media.ToneGenerator
import android.widget.Toast


class Alarm : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
       val mp = MediaPlayer.create(context, R.raw.beep);
        mp.start();
        Toast.makeText(context, "Alarm", Toast.LENGTH_LONG).show()
    }
}
