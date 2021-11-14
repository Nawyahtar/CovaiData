package com.example.covaidata

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CovidCallBack{

@GET("countries/{name}")
fun getCountryName(
    @Path("name")name :String
):Call<CovidResponse>

@GET("countries")
fun getAllCountries():Call<List<CovidResponse>>
}