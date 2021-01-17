package de.backgroundoperrations.mocoworkshop.ui.threads

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import de.backgroundoperrations.mocoworkshop.R
import de.backgroundoperrations.mocoworkshop.ui.work.WorkViewModel


class ThreadFragment : Fragment() {

    private lateinit var threadViewModel: ThreadViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        threadViewModel =
            ViewModelProvider(this).get(ThreadViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_thread, container, false)

        return root
    }
}