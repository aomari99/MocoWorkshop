package de.backgroundoperrations.mocoworkshop.ui.work

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import de.backgroundoperrations.mocoworkshop.R

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

        return root
    }
}