package de.backgroundoperrations.mocoworkshop.ui.myservices

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.provider.Settings
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.InetAddress
import java.net.Socket
import java.nio.charset.StandardCharsets
import de.backgroundoperrations.mocoworkshop.R
import de.backgroundoperrations.mocoworkshop.ui.work.WorkFragment


class MyService :Service() {
    override fun onCreate() {
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        createNotificationChannel()
        val pendingIntent: PendingIntent =
                Intent(this, WorkFragment::class.java).let { notificationIntent ->
                    PendingIntent.getActivity(this, 0, notificationIntent, 0)
                }

        val notification: Notification = NotificationCompat.Builder(this, "myservice")
                .setContentTitle("Warten auf helfer")
                .setContentText("Bitte Warten Sie")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentIntent(pendingIntent)
                .setTicker("Tick")
                .build()

// Notification ID cannot be 0.
        startForeground(99, notification)
        GlobalScope.launch{
            //    createNotificationChannel()
            var mRun = true;

            var charsRead = 0
            val buffer =
                    CharArray(2024) //choose your buffer size if you need other than 1024 val serverAddr = InetAddress.getByName("192.168.50.75");
            //val client = Socket("192.168.50.75", 1234)
            Log.e("TCP Client", "C: Connecting...");
            val socket =  Socket("192.168.50.75",8888);

            while(mRun) {
                val mBufferIn = BufferedReader(InputStreamReader(socket.getInputStream(), StandardCharsets.US_ASCII));


                charsRead = mBufferIn.read(buffer)
                var mServerMessage: String? = String(buffer).substring(0, charsRead)
                if (mServerMessage != null)
                    GlobalScope.launch{ shownote(mServerMessage)
                        Log.e("response",mServerMessage)}

            }

        }
        return  START_NOT_STICKY
    }
    var id = 100
    fun shownote(string: String){

        var builder = NotificationCompat.Builder(this, "myservice")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("Helfer gefunden")
                .setContentText("$string würde gerne ihren Einkauf erledigen")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        with(NotificationManagerCompat.from(this)) {
            // notificationId is a unique int for each notification that you must define
            id++;
            notify(id, builder.build())

        }
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name =("myservicetest")
            val descriptionText = "myservice"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("myservice", name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                    getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    override fun onDestroy() {
        Intent(this, MyService::class.java).also { intent ->
            startService(intent)
        }
    }


}