package de.backgroundoperrations.mocoworkshop.ui.myservices

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.Socket
import java.nio.charset.StandardCharsets
import de.backgroundoperrations.mocoworkshop.R
import kotlinx.coroutines.*

class MyService : Service() {

    //Begleit Objekt
    companion object {

        //Forgroudservice Channel ID
        private  var ID = 99
       // t45xvxe1amipu7ef.myfritz.net
        //IP-Adresse des Servers in unserem Fall die des eigenen Geräts
        private val SERVER = "192.168.50.81"

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
        Log.i("Hostname", "$SERVER")
        //Benachrichtung für den User das er mit dem Server Connected ist und auf Antwort einens Users wartet
        val notification: Notification = NotificationCompat.Builder(this, CHANNEL_ID_MY_SERVICE)
                .setContentTitle("Warten auf helfer")
                .setContentText("Bitte Warten Sie")
                .setSmallIcon(R.drawable.ic_menu_message)
                .setContentIntent(pendingIntent)
                .build()


        //Starten des Vordergrund Dienstes, Falls dies nicht gestartet wird, wird der Service nach 5 Sekunden beendet
        startForeground(ID, notification)


        CoroutineScope(Dispatchers.IO).launch {
        //    delay(6000)
            val mRun = true;
            var charsRead = 0
            var buffer = CharArray(BUFFERSIZE)
            Log.e("TCP Client", "C: Connecting...");
          //Erstellen des Socket mit der im Companion Objekt angelegten IP und dem Port
            val socket = Socket(SERVER, PORT);
            while (mRun) {
                //Einlesen der gesendeten Daten vom C TCP-Server
                val mBufferIn = BufferedReader(InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));

                charsRead = mBufferIn.read(buffer)
                if(charsRead<0) continue
                //Umwandeln der empfangenen Daten in einen String
                val mServerMessage: String? = String(buffer).substring(0, charsRead)
                buffer= CharArray(BUFFERSIZE)
                if (mServerMessage != null)
                    CoroutineScope(Dispatchers.IO).launch {
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
                .setSmallIcon(R.drawable.ic_menu_message)
                .setContentTitle("Helfer gefunden")
                .setContentText("$string würde gerne ihren Einkauf erledigen")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        with(NotificationManagerCompat.from(this)) {

            //Benachrichten des Clients
            notify(ID, builder.build())
            ID++
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