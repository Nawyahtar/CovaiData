package com.example.covaidata

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.zip.Inflater

class RecyclerViewActivity :AppCompatActivity(),RecyclerViewAdapter.onDetailItemClickListener{
    private var covidResponse = mutableListOf<CovidResponse>()

    private lateinit var adapter:RecyclerViewAdapter
    private val rvCovid by lazy {
        findViewById<RecyclerView>(R.id.rvCovid) }

     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_covid_recycler_view)
         setTitle("CovidData")


         getData()//Url and get data form API


         adapter=RecyclerViewAdapter(covidResponse,this)
         rvCovid.adapter=adapter
         rvCovid.layoutManager=LinearLayoutManager(this,RecyclerView.VERTICAL,false)

         //Intent to MainActivity
         val buttonGo=findViewById<MaterialButton>(R.id.buttonGo)
         buttonGo.setOnClickListener{
             goMain()
         }
         //Sort by name
         val buttonSortByName=findViewById<MaterialButton>(R.id.buttonSortByName)
         buttonSortByName.setOnClickListener{
             getSortByName()
         }

         val buttonSortByUpdated=findViewById<MaterialButton>(R.id.buttonSortByUpdated)
         buttonSortByUpdated.setOnClickListener{
             getSortByUpdated()
         }



    }



    private fun getSortByUpdated() {

        covidResponse.sortBy {
            it.updated
        }
        adapter= RecyclerViewAdapter(covidResponse,this)
        rvCovid.adapter=adapter
        rvCovid.layoutManager=LinearLayoutManager(this,RecyclerView.VERTICAL,false)

    }

    private fun getSortByName() {
        covidResponse.sortBy {
            it.country
        }
        adapter= RecyclerViewAdapter(covidResponse,this)
        rvCovid.adapter=adapter
        rvCovid.layoutManager=LinearLayoutManager(this,RecyclerView.VERTICAL,false)


    }

    private fun goMain() {
       val intent=Intent(this,MainActivity::class.java)
       startActivity(intent)

    }


    private fun getData() {
        val retrofit = Retrofit.Builder()
                .baseUrl("https://corona.lmao.ninja/v2/")
                .addConverterFactory(MoshiConverterFactory.create())
                .client(OkHttpClient())
                .build()

            val covidCallBack = retrofit.create(CovidCallBack::class.java)
            covidCallBack.getAllCountries().enqueue(object :Callback<List<CovidResponse>>{
                override fun onResponse(
                    call: Call<List<CovidResponse>>,
                    response: Response<List<CovidResponse>>
                ) {
                    if (response.isSuccessful){
                        response.body()?.let { list: List<CovidResponse> ->
                            covidResponse.clear()
                            covidResponse.addAll(list)
                            adapter.notifyDataSetChanged()


                        }
                    }

                }

                override fun onFailure(call: Call<List<CovidResponse>>, t: Throwable) {
                    t.printStackTrace()
                }


            })


    }

    override fun onItemClick(item: CovidResponse, position: Int) {
        val intent=Intent(this,getDetailActivity::class.java)
        intent.putExtra("name",item.country)
        intent.putExtra("flag",item.countryInfo.flag)
        intent.putExtra("cases",item.cases.toString())
        intent.putExtra("recovered",item.recovered.toString())
        intent.putExtra("deaths",item.deaths.toString())
        intent.putExtra("population",item.population.toString())
        intent.putExtra("active",item.active.toString())
        intent.putExtra("code",item.countryInfo.iso3)
        intent.putExtra("continent",item.continent)

        startActivity(intent)

    }



}