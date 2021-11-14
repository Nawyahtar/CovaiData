package com.example.covaidata

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import kotlin.math.atan


class RecyclerViewAdapter(private val arrayList: List<CovidResponse>,var clicklistener:onDetailItemClickListener): RecyclerView.Adapter<RecyclerViewAdapter.CovidViewHolder>(){

    class CovidViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){

        val tvCountryName=itemView.findViewById<TextView>(R.id.tvCountryName)
        val ivFlag=itemView.findViewById<ImageView>(R.id.ivFlag)
        val tvTotalCases=itemView.findViewById<TextView>(R.id.tvTotalCases)
        val tvTotalRecovered=itemView.findViewById<TextView>(R.id.tvTotalRecovered)
        val tvTotalDeath=itemView.findViewById<TextView>(R.id.tvTotalDeath)
        val tvTodayCases=itemView.findViewById<TextView>(R.id.tvTodayCases)
        val tvTodayRecovered=itemView.findViewById<TextView>(R.id.tvTodayRecovered)
        val tvTodayDeath=itemView.findViewById<TextView>(R.id.tvTodayDeath)

        fun initialize(item: CovidResponse,action:onDetailItemClickListener){
            tvCountryName.text=item.country


            if (Math.abs(item.cases / 1000) > 1) {
                val casesKilo=(item.cases/1000).toString()+"K"
                tvTotalCases.text=casesKilo

            }else{tvTotalCases.text=item.cases.toString()}

            if(Math.abs(item.recovered/1000)>1){
                val recoveredKilo=(item.recovered/1000).toString()+"K"
                  tvTotalRecovered.text=recoveredKilo
            }else{tvTotalRecovered.text=item.recovered.toString()}


            if(item.deaths==0){
                 tvTotalDeath.text="-"
            }else{tvTotalDeath.text=item.deaths.toString()}

            if(item.todayCases==0){
                tvTodayCases.text="-"
            }else{tvTodayCases.text=item.todayCases.toString()}

            if(item.todayRecovered==0){
                tvTodayRecovered.text="-"
            }else{tvTodayRecovered.text=item.todayRecovered.toString()}

            if(item.todayDeaths==0){
                tvTodayDeath.text="-"
            }else{tvTodayDeath.text=item.todayDeaths.toString()}


            Glide.with(itemView.context).load(item.countryInfo.flag).into(ivFlag)


             itemView.setOnClickListener{
                 action.onItemClick(item,bindingAdapterPosition)
             }

        }



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CovidViewHolder {
       val itemView=LayoutInflater.from(parent.context).inflate(R.layout.activity_item_recycler_view,parent,false)
        return CovidViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CovidViewHolder, position: Int) {
        holder.initialize(arrayList.get(position),clicklistener)



    }

    override fun getItemCount(): Int {
        return arrayList.size


    }
    interface onDetailItemClickListener{
        fun onItemClick(item:CovidResponse,position: Int)
    }

}