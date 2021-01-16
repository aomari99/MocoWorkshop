package de.backgroundoperrations.mocoworkshop.ui.myservices

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import de.backgroundoperrations.mocoworkshop.R
import de.backgroundoperrations.mocoworkshop.ui.threads.ThreadViewModel


class MyServicesFragment : Fragment() {
    private lateinit var myservicesViewModel: MyServicesViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        myservicesViewModel =
            ViewModelProvider(this).get(MyServicesViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_my_services, container, false)

        return root
    }
}