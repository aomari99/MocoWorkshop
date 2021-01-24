package de.backgroundoperrations.mocoworkshop.ui.myservices

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.Socket
import java.nio.charset.StandardCharsets
import de.backgroundoperrations.mocoworkshop.R
import kotlinx.coroutines.NonCancellable.cancel

class MyService : Service() {

    //Begleit Objekt
    companion object {

        //Forgroudservice Channel ID
        private const val ID = 99

        //IP-Adresse des Servers in unserem Fall die des eigenen Geräts
        private const val SERVER = "192.168.50.75"

        //Port auf welchem der Server lauscht
        private const val PORT = 8888

        //Nachrichten Channel von myservice
        private const val CHANNEL_ID_MY_SERVICE="myservice"

        //Größe des Buffers für den Nachrichten Empfang
        private const val BUFFERSIZE=2048
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        createNotificationChannel()
        val pendingIntent: PendingIntent =
                Intent(this, MyServicesFragment::class.java).let { notificationIntent ->
                    PendingIntent.getActivity(this, 0, notificationIntent, 0)
                }

        //Benachrichtung für den User das er mit dem Server Connected ist und auf Antwort einens Users wartet
        val notification: Notification = NotificationCompat.Builder(this, CHANNEL_ID_MY_SERVICE)
                .setContentTitle("Warten auf helfer")
                .setContentText("Bitte Warten Sie")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build()


        //Starten des Vordergrund Dienstes, Falls dies nicht gestartet wird, wird der Service nach 5 Sekunden beendet
      startForeground(ID, notification)
        GlobalScope.launch {
            val mRun = true;

            var charsRead = 0
            val buffer = CharArray(BUFFERSIZE)
            Log.e("TCP Client", "C: Connecting...");
          //Erstellen des Socket mit der im Companion Objekt angelegten IP und dem Port
            val socket = Socket(SERVER, PORT);
            while (mRun) {
                //Einlesen der gesendeten Daten vom C TCP-Server
                val mBufferIn = BufferedReader(InputStreamReader(socket.getInputStream(), StandardCharsets.US_ASCII));

                charsRead = mBufferIn.read(buffer)

                //Umwandeln der empfangenen Daten in einen String
                val mServerMessage: String? = String(buffer).substring(0, charsRead)
                if (mServerMessage != null)
                    GlobalScope.launch {
                        //Ausführen der Notification das ein Helfer gefunden wurde, mit übergabe des Namens
                        shownote(mServerMessage)
                        Log.i("connectserver", mServerMessage)
                    }

            }

        }
        return START_NOT_STICKY
    }

    fun shownote(string: String) {

        val builder = NotificationCompat.Builder(this, CHANNEL_ID_MY_SERVICE)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("Helfer gefunden")
                .setContentText("$string würde gerne ihren Einkauf erledigen")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)
        with(NotificationManagerCompat.from(this)) {

            //Benachrichten des Clients
            notify(ID, builder.build())

        }
    }
    //Erstellen des Notification Channels
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = ("myservicetest")
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
            stopService(intent)

        }
    }


}