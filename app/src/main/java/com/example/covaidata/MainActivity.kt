package com.example.covaidata

import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.format.DateUtils
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethod
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.squareup.moshi.Moshi
import okhttp3.*
import retrofit2.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.IOException
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*


class MainActivity : AppCompatActivity() {
    private val tvUpdated by lazy {
        findViewById<TextView>(R.id.tvUpdated)
    }
    private val tvCountry by lazy {
        findViewById<TextView>(R.id.tvCountry)
    }
    private val tvCases by lazy {
        findViewById<TextView>(R.id.tvCases)
    }
    private val tvDeath by lazy {
        findViewById<TextView>(R.id.tvDeath)
    }
    private val tvActive by lazy {
        findViewById<TextView>(R.id.tvActive)
    }
    private val tvCountryCode by lazy {
        findViewById<TextView>(R.id.tvCountryCode)
    }
    private val tvRecovered by lazy {
        findViewById<TextView>(R.id.tvRecovered)
    }
    private val tvPopulation by lazy {
        findViewById<TextView>(R.id.tvPopulation)
    }
    private val editTextSearch by lazy {
        findViewById<EditText>(R.id.editTextSearch)
    }
    private val tvContinent by lazy {
        findViewById<TextView>(R.id.tvContinent)
    }
    private val buttonSearch by lazy {
        findViewById<Button>(R.id.buttonSearch)
    }

    private val imageViewFlag by lazy {
        findViewById<ImageView>(R.id.imageViewFlag)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var actionBar=supportActionBar
        if(actionBar!=null){
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back_ios_24)
            actionBar.setDisplayHomeAsUpEnabled(true)
        }
        setTitle("Search Country")

        tvActive.visibility=View.GONE
        tvRecovered.visibility=View.GONE
        tvDeath.visibility=View.GONE
        tvCases.visibility=View.GONE
        tvCountry.visibility=View.GONE
        tvPopulation.visibility=View.GONE
        tvUpdated.visibility=View.GONE
        tvCountryCode.visibility=View.GONE
        imageViewFlag.visibility=View.GONE
        tvContinent.visibility=View.GONE

        buttonSearch.setOnClickListener{
            getData()
            editTextSearch.onEditorAction(EditorInfo.IME_ACTION_DONE)

        }




    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home->{
                finish()
                return true

            }
        }
        return super.onOptionsItemSelected(item)
    }




    private   fun getData() {

        val name=editTextSearch.text.toString()
        val retrofit=Retrofit.Builder()
            .baseUrl("https://corona.lmao.ninja/v2/")
            .addConverterFactory(MoshiConverterFactory.create())
            .client(OkHttpClient())
            .build()

        val covidCallBack=retrofit.create(CovidCallBack::class.java)

  covidCallBack.getCountryName(name).enqueue(object:Callback<CovidResponse>{
            override fun onResponse(call: Call<CovidResponse>, response: Response<CovidResponse>) {
              if(response.isSuccessful){
                  response.body()?.let { covidResponse ->

                      showData(
                              updated=covidResponse.updated,
                              country=covidResponse.country,
                              countryCode=covidResponse.countryInfo.iso3.orEmpty(),
                              population=covidResponse.population.toString(),
                              cases=covidResponse.cases.toString(),
                              recovered=covidResponse.recovered.toString(),
                              active=covidResponse.active.toString(),
                              deaths=covidResponse.deaths.toString(),
                              flag=covidResponse.countryInfo.flag,
                              continent=covidResponse.continent




                          )

                  }
              }else{
                  Toast.makeText(this@MainActivity,"This country does not exist",Toast.LENGTH_LONG).show()
              }
            }

            override fun onFailure(call: Call<CovidResponse>, t: Throwable) {
               t.printStackTrace()
            }

        })
    }

    @SuppressLint("NewApi")
    private fun showData(
        updated: String,
        country: String,
        countryCode: String,
        population: String,
        cases: String,
        recovered: String,
        active: String,
        deaths: String,
        flag: String,
        continent:String
    ) {

       val ago= DateUtils.getRelativeTimeSpanString(updated.toLong(),System.currentTimeMillis(),0L,DateUtils.FORMAT_ABBREV_ALL)


        tvUpdated.text="Updated::$ago"
        tvCountry.text="Country::$country"
        tvCountryCode.text="CountryCode::$countryCode"
        tvPopulation.text= "Population::$population"
        tvCases.text= "Cases::$cases"
        tvRecovered.text= "Recovered::$recovered"
        tvActive.text= "Active::$active"
        tvDeath.text= "Deaths::$deaths"
        Glide.with(this).load(flag).into(imageViewFlag)
        tvContinent.text="Continent::$continent"


        tvActive.visibility=View.VISIBLE
        tvRecovered.visibility=View.VISIBLE
        tvDeath.visibility=View.VISIBLE
        tvCases.visibility=View.VISIBLE
        tvCountry.visibility=View.VISIBLE
        tvPopulation.visibility=View.VISIBLE
        tvUpdated.visibility=View.VISIBLE
        tvCountryCode.visibility=View.VISIBLE
        imageViewFlag.visibility=View.VISIBLE
        tvContinent.visibility=View.VISIBLE

    }


}




