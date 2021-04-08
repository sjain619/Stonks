package com.eit.stonks.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.eit.stonks.model.StonksResponse
import org.json.JSONObject

class StonksViewModel(application: Application) : AndroidViewModel(application) {

    val fileLiveData: MutableLiveData<List<StonksResponse>> = MutableLiveData()

    fun getCompanyNameList() {
        Thread() {
            val stonkList = mutableListOf<StonksResponse>()
            val inputStream = getApplication<Application>().assets.open("companies.json")
            val length = inputStream.available()
            val fileBytes = ByteArray(length)
            inputStream.read(fileBytes)
            inputStream.close()

            val responseJson = String(fileBytes)
            val responseDataObj = JSONObject(responseJson)
            val keys: Iterator<*> = responseDataObj.keys()
            while (keys.hasNext()) {
                val currentDynamicKey = keys.next() as String
                val copmpanyObject: JSONObject = responseDataObj.getJSONObject(currentDynamicKey)
                var marketCap: Long = 0
                try {
                    marketCap = copmpanyObject.getLong("MarketCap")
                } catch (e: Exception) {
                }
                var stonks = StonksResponse(
                        Change = copmpanyObject.getDouble("Change"),
                        ChangePercent = copmpanyObject.getDouble("ChangePercent"),
                        ChangePercentYTD = copmpanyObject.getDouble("ChangePercentYTD"),
                        ChangeYTD = copmpanyObject.getDouble("ChangeYTD"),
                        High = copmpanyObject.getDouble("High"),
                        LastPrice = copmpanyObject.getDouble("LastPrice"),
                        Low = copmpanyObject.getDouble("Low"),
                        MSDate = copmpanyObject.getInt("MSDate"),
                        MarketCap = marketCap,
                        Name = copmpanyObject.getString("Name"),
                        Open = copmpanyObject.getDouble("Open"),
                        Symbol = copmpanyObject.getString("Symbol"),
                        Volume = copmpanyObject.getInt("Volume")
                )

                stonkList.add(stonks)
            }
            fileLiveData.postValue(stonkList)

        }.start()
    }

}