package de.backgroundoperrations.mocoworkshop.ui.work


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import de.backgroundoperrations.mocoworkshop.R
import java.util.concurrent.TimeUnit


class WorkFragment : Fragment() {
    companion object{
        lateinit var workmanager :WorkManager
    }


    private lateinit var workViewModel: WorkViewModel
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        workViewModel =
            ViewModelProvider(this).get(WorkViewModel::class.java)


        val root = inflater.inflate(R.layout.fragment_work, container, false)
   //     val constraints= Constraints.Builder().setRequiresCharging(true).build()
        val request= OneTimeWorkRequestBuilder<WorkOneTimeRequest>()
          //      .setConstraints(constraints)
                .build()
        val request2= PeriodicWorkRequestBuilder<WorkPeriodical>(15,TimeUnit.MINUTES).build()
        workmanager=WorkManager.getInstance(root.context)

        val workmanagerbtn =root.findViewById<Button>(R.id.startworkmanager_button)
        val cancelbutton = root.findViewById<Button>(R.id.workmanagercancel_button)
        workmanagerbtn.setOnClickListener {
            workmanager.enqueue(request)
            workmanager.enqueue(request2)
        }

        cancelbutton.setOnClickListener{
            workmanager.cancelAllWork()
        }

        return root
    }
}