package de.backgroundoperrations.mocoworkshop.ui.myservices

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import de.backgroundoperrations.mocoworkshop.R
import android.content.Intent
import androidx.core.content.ContextCompat


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
        val conbtn= root.findViewById<Button>(R.id.myservice_connection_button)
        conbtn.setOnClickListener() {

            val startIntent =Intent(this.context,MyService::class.java)
            ContextCompat.startForegroundService(root.context,startIntent)

        }


        return root
    }
}