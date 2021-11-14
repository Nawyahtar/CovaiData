package com.example.covaidata

import android.app.UiModeManager
import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.format.DateUtils
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.View.inflate
import android.widget.*
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.res.ComplexColorCompat.inflate
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.zip.Inflater

class getDetailActivity : AppCompatActivity() {


    private val tvCountryNameDetail by lazy {
        findViewById<TextView>(R.id.tvCountryNameDetail)
    }
    private val tvUpdated by lazy {
        findViewById<TextView>(R.id.tvUpdated)
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

    private val tvContinent by lazy {
        findViewById<TextView>(R.id.tvContinent)
    }

    private val imageViewFlag by lazy {
        findViewById<ImageView>(R.id.imageViewFlag)
    }
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_get_detail)




        var actionBar=supportActionBar
        if(actionBar!=null){
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back_ios_24)
            actionBar.setDisplayHomeAsUpEnabled(true)
        }

        setTitle("CountryDetail")


        tvCountryNameDetail.text=getIntent().getStringExtra("name")
        val icon=getIntent().getStringExtra("flag")
        Glide.with(this).load(icon).into(imageViewFlag)


        val countryCode=getIntent().getStringExtra("code")
        tvCountryCode.text="CountryCode::$countryCode"

        val population=getIntent().getStringExtra("population")
        tvPopulation.text="Population::$population"

        val cases=getIntent().getStringExtra("cases")
        tvCases.text="Cases::$cases"
        val recovered=getIntent().getStringExtra("recovered")
        tvRecovered.text="Recovered::$recovered"

        val death=getIntent().getStringExtra("deaths")
        tvDeath.text="Deaths::$death"

        val active=getIntent().getStringExtra("active")
        tvActive.text="Active::$active"

        val continent=getIntent().getStringExtra("continent")
        tvContinent.text="Continent::$continent"


    }





    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home->{
                finish()
                return true

            }
            R.id.menuDark->{



            }
        }


        return super.onOptionsItemSelected(item)
    }


}