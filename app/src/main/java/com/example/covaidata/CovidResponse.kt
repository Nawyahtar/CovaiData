package com.example.covaidata

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CovidResponse(
    @Json(name = "updated")val updated:String,
    @Json(name = "country")val country:String,
    @Json(name="countryInfo")val countryInfo:CovidResponseCountyInfo,
    @Json(name = "cases")val cases:Int,
    @Json(name = "deaths")val deaths:Int,
    @Json(name = "recovered")val recovered:Int,
    @Json(name = "active")val active:Int,
    @Json(name = "population") val population:Int,
    @Json(name = "todayCases")val todayCases:Int,
    @Json(name = "todayDeaths")val todayDeaths:Int,
    @Json(name = "todayRecovered")val todayRecovered:Int,
    @Json(name="continent")val continent:String


)
@JsonClass(generateAdapter = true)
data class CovidResponseCountyInfo(
    @Json(name="iso3")val iso3:String?,
    @Json(name="flag")val flag:String

)
