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

    private lateinit var workViewModel: WorkViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        workViewModel =
            ViewModelProvider(this).get(WorkViewModel::class.java)


        val root = inflater.inflate(R.layout.fragment_work, container, false)
        val request= OneTimeWorkRequestBuilder<WorkOneTimeRequest>().build()
        val request2= PeriodicWorkRequestBuilder<WorkPeriodical>(15,TimeUnit.MINUTES).build()
        val workManageronetime= WorkManager.getInstance(root.context)
        val workManagerperiodical=WorkManager.getInstance(root.context)

        val btnonetime =root.findViewById<Button>(R.id.one_time_request_button)
        btnonetime.setOnClickListener {
            workManageronetime.enqueue(request)
            workManagerperiodical.enqueue(request2)
        }

        return root
    }
}