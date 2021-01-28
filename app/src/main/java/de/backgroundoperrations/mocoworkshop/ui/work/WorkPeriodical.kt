package de.backgroundoperrations.mocoworkshop.ui.work

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.core.app.CoreComponentFactory
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import de.backgroundoperrations.mocoworkshop.R

class WorkPeriodical(context: Context, workerParams: WorkerParameters) : CoroutineWorker(context, workerParams) {
companion object{
    private const val WORK_MANAGER_PERIODIC_CHANNEL_ID= "CHANNEL_ID_WORK_MANAGER_PERIODICAL"
    private const val WORK_MANAGER_PERIODICAL_CHANNEL_NAME="WORK_MANAGER_PERIODICAL"
    var tageinquarantaene=14
    var counter=0;
}
   override suspend fun doWork(): Result {
        if(tageinquarantaene!=0){
            createNotification("Qurantäne Status","Sie befinden sich noch $tageinquarantaene Tage in Qurantäne")
        }else{
            createNotification("Qurantäne Status","Sie befinden sich ab heute nicht mehr in Qurantäne")
            tageinquarantaene=14
            counter=0
            WorkFragment.workmanager.cancelAllWork()
        }
        counter++
        if(counter==1){
            tageinquarantaene--
            counter=0
        }

        Log.i("Qurantaenestatus","Noch in Qurantäne $tageinquarantaene")
        return Result.success()

    }
    fun createNotification(title:String,description:String) {

        var notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(WORK_MANAGER_PERIODIC_CHANNEL_ID, WORK_MANAGER_PERIODICAL_CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(notificationChannel)
        }

        val notificationBuilder = NotificationCompat.Builder(applicationContext, WORK_MANAGER_PERIODIC_CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(description)
                .setSmallIcon(R.drawable.ic_menu_work)

        notificationManager.notify(2, notificationBuilder.build())
    }
}