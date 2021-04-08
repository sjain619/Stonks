package com.eit.stonks.view.activity

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.eit.stonks.R
import com.eit.stonks.model.StonksResponse
import com.eit.stonks.view.adapter.StonksAdapter
import com.eit.stonks.view.fragment.DetailsFragment
import com.eit.stonks.viewmodel.StonksViewModel
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity(), StonksAdapter.StonksDelegate {

    private lateinit var stockRecyclerView:RecyclerView
    private lateinit var stonksAdapter: StonksAdapter

    private lateinit var searchStocks: EditText
    private lateinit var detailFrame: FrameLayout
    var searchTimer:Timer = Timer()
    var stonksList: ArrayList<StonksResponse> = ArrayList();

    private lateinit var viewModel: StonksViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()

        viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(this.application).create(StonksViewModel::class.java)

        viewModel.getCompanyNameList()
        viewModel.fileLiveData.observe(this, {
            stonksAdapter.updateList(it)
        })

        searchStocks.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(text: Editable?) {


            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //Not used
            }

            override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {

                searchTimer.cancel()
                searchTimer = Timer()

                searchTimer.schedule(
                    object : TimerTask() {
                        override fun run() {
                         runOnUiThread {
                                if (text != null) {
                                    if(text.isEmpty()){
                                        stockRecyclerView.visibility = View.GONE
                                    }else{
                                        stockRecyclerView.visibility = View.VISIBLE
                                    }
                                }
                                stonksAdapter.filter.filter(text.toString())
                            }
                        }
                    }, 100
                )
            }
        })
    }


    private fun init() {
        stockRecyclerView = findViewById(R.id.rv_stonk)
        searchStocks = findViewById(R.id.etv_searchBar)
        detailFrame = findViewById(R.id.detail_frame)
        stockRecyclerView.visibility = View.GONE
        stonksAdapter = StonksAdapter(stonksList, this)
        stockRecyclerView.adapter = stonksAdapter
    }



    override fun showCompanyNames(stonksResponse: StonksResponse) {
        searchStocks.setText(" ")
        val fragment = DetailsFragment.newInstance(stonksResponse)
        supportFragmentManager
                .beginTransaction()
                .setCustomAnimations(
                        android.R.anim.slide_in_left,
                        android.R.anim.slide_out_right,
                        android.R.anim.slide_in_left,
                        android.R.anim.slide_out_right
                )
                .add(R.id.detail_frame, fragment)
                .addToBackStack(fragment.tag)
                .commit()

    }
}