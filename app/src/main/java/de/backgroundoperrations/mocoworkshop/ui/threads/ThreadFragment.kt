package de.backgroundoperrations.mocoworkshop.ui.threads

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import de.backgroundoperrations.mocoworkshop.R
import de.backgroundoperrations.mocoworkshop.ui.work.WorkViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.io.BufferedReader
import java.net.HttpURLConnection
import java.net.URL
import android.widget.*

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
        var connect  = root.findViewById<Button>(R.id.connect);
        var locations : MutableList<String> = mutableListOf()
        GlobalScope.launch{

            var reque = URL("https://services7.arcgis.com/mOBPykOjAyBO2ZKk/arcgis/rest/services/RKI_Landkreisdaten/FeatureServer/0/query?where=1%3D1&outFields=GEN&returnGeometry=false&outSR=4326&f=json")
            Log.d("json", reque.toString())
            val conn: HttpURLConnection = reque.openConnection() as HttpURLConnection
            conn.setConnectTimeout(1000) // timing out in a minute
            val json = conn.inputStream.bufferedReader().use(BufferedReader::readText)

            Log.d("json", json)
            val field = JSONObject(json).getJSONArray("features")
            for (i in 0..(field.length()-1)) {
                val attr = field.getJSONObject(i)
                val ele = attr.getJSONObject("attributes")
                val cases = ele.getString("GEN")
                locations.add(cases)
            }
        }
        val ad : ArrayAdapter<String> = ArrayAdapter<String>(root.context,R.layout.support_simple_spinner_dropdown_item,locations)

        var iptxt = root.findViewById<AutoCompleteTextView>(R.id.iptxt)
        iptxt.setAdapter(ad)
        var hello = root.findViewById<TextView>(R.id.info)
        connect.setOnClickListener() {
            var i = 0;
            GlobalScope.launch {
                while (true) {
                    i++;
                }
            }.start()

            Thread {

                var reque = URL("https://services7.arcgis.com/mOBPykOjAyBO2ZKk/arcgis/rest/services/RKI_Landkreisdaten/FeatureServer/0/query?where=GEN='${iptxt.text}'&outFields=cases7_per_100k_txt&returnGeometry=false&outSR=4326&f=json")
                Log.d("json", reque.toString())
                val conn: HttpURLConnection = reque.openConnection() as HttpURLConnection
                conn.setConnectTimeout(1000) // timing out in a minute
                val json = conn.inputStream.bufferedReader().use(BufferedReader::readText)
                val field = JSONObject(json).getJSONArray("features")
                val attr = field.getJSONObject(0)
                val ele = attr.getJSONObject("attributes")
                val cases = ele.getString("cases7_per_100k_txt")

                hello.text = "$cases Fälle pro 100.000 Einwohner in den Letzen 7 Tagen"

            }.start()

    }
        return root
}}