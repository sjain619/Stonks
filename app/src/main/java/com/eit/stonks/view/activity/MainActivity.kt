package com.eit.stonks.view.activity

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.eit.stonks.R
import com.eit.stonks.model.StonksResponse
import com.eit.stonks.view.adapter.StonksAdapter
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.*

class MainActivity : AppCompatActivity(), StonksAdapter.StonksDelegate {

    private lateinit var stockList:RecyclerView
    private lateinit var searchStocks: EditText
    private lateinit var detailFrame: FrameLayout
    var searchTimer: Timer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()

        //use okhttp
        object : Thread() {
            override fun run() {
                super.run()
                openConnection()
            }
        }.start()

        searchStocks.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(text: Editable?) {


            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //Not used
                Log.d("TAG_X", "before: $p0")
            }

            override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //Not used
                Log.d("TAG_X", "onTextChanged: ${text}")
                searchTimer.cancel()
                searchTimer = Timer()

                searchTimer.schedule(
                        object : TimerTask() {
                            override fun run() {
                                Log.d("TAG_X", "Hello....")
                                viewModel.getSearchResults(text.toString())
                            }

                        }, 3000
                )
            }
        })
    }

    private fun openConnection() {
        try {
            val stockUrl = URL("https://www.stocksapi.com/data/2.5/stockinfo/q?")
            val urlConnection = stockUrl.openConnection() as HttpURLConnection
            urlConnection.requestMethod = "GET"
            urlConnection.connect()
            val reader = BufferedReader(InputStreamReader(urlConnection.inputStream))
            var input: String?
            val builder = StringBuilder()
            while (reader.readLine().also { input = it } != null) {
                builder.append(input)
            }
            Log.d("TAG_X", builder.toString())
            urlConnection.disconnect()
            val gson = Gson()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    private fun init() {
        stockList = findViewById(R.id.rv_stockList)
        searchStocks = findViewById(R.id.etv_searchBar)
        detailFrame = findViewById(R.id.detail_frame)
    }

    override fun showCompanyNames(weatherResult: StonksResponse) {
        TODO("Not yet implemented")
    }
}