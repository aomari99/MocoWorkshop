package de.backgroundoperrations.mocoworkshop.ui.work

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import de.backgroundoperrations.mocoworkshop.R

class WorkOneTimeRequest(context: Context,workerParams: WorkerParameters): Worker(context, workerParams) {
   companion object{
       private const val WORK_MANAGER_CHANNEL_ID="CHANNEL_ID_WORK_MANAGER"
       private const val WORK_MANAGER_CHANNEL_NAME="WORK_MANAGER"
   }

    override fun doWork(): Result {
        createNotification("Einweisung in Quarantäne","Sie befinden sich ab sofort in Quarantäne")
        return Result.success()
    }

    fun createNotification(title:String,description:String){

        var notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val notificationChannel = NotificationChannel(WORK_MANAGER_CHANNEL_ID, WORK_MANAGER_CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(notificationChannel)
        }

        val notificationBuilder= NotificationCompat.Builder(applicationContext, WORK_MANAGER_CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(description)
            .setSmallIcon(R.drawable.ic_launcher_background)

        notificationManager.notify(1,notificationBuilder.build())
    }
}