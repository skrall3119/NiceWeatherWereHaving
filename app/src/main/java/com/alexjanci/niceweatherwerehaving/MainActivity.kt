package com.alexjanci.niceweatherwerehaving

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Criteria
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.app.ActivityCompat
import com.alexjanci.niceweatherwerehaving.Helper.Helper
import com.alexjanci.niceweatherwerehaving.Model.Coord
import com.alexjanci.niceweatherwerehaving.Model.Main
import com.alexjanci.niceweatherwerehaving.Model.OpenWeatherMap
import com.alexjanci.niceweatherwerehaving.common.Common
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), LocationListener {

    var locationManager: LocationManager? = null
    var provider:String? = null
    var lat:Double? = null
    var lng:Double? = null
    val common: Common = Common()

    var MY_PERMISSION: Int = 0
    var openWeatherMap: OpenWeatherMap = OpenWeatherMap()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        provider = locationManager!!.getBestProvider(Criteria(), false)

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this, arrayOf<String>(
                    Manifest.permission.INTERNET,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_NETWORK_STATE,
                    Manifest.permission.SYSTEM_ALERT_WINDOW,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ), MY_PERMISSION
            )
        }


        val location: Location? = locationManager!!.getLastKnownLocation(Criteria().toString())
        if (location == null) {
            Log.e("Tag", "No Location")
        }

    }

    override fun onPause() {
        super.onPause()
        locationManager!!.removeUpdates(this)
    }

    override fun onResume() {
        super.onResume()
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this, arrayOf<String>(
                    Manifest.permission.INTERNET,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_NETWORK_STATE,
                    Manifest.permission.SYSTEM_ALERT_WINDOW,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ), MY_PERMISSION
            )
        }
        locationManager!!.requestLocationUpdates(provider!!, 400, 1.toFloat(), this)
    }

    override fun onLocationChanged(p0: Location?) {
        if (p0 != null) {
            lat = p0.latitude
            lng = p0.longitude
            GetWeather().execute(common.apiRequest(lat.toString(),lng.toString()))
        }
    }

    override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {
        TODO("Not yet implemented")
    }

    override fun onProviderEnabled(p0: String?) {
        TODO("Not yet implemented")
    }

    override fun onProviderDisabled(p0: String?) {

    }

   inner class GetWeather : AsyncTask<String, Void, String>() {
       val picasso:Picasso = Picasso.get()

       override fun onPreExecute() {
            super.onPreExecute()
        }

        override fun doInBackground(vararg p0: String?): String {
            var stream: String? = null
            val urlString = p0[0].toString()

            val http = Helper()
            stream = http.getHTTPData(urlString!!)
            return stream
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            val gson = Gson()
            openWeatherMap = gson.fromJson(result, OpenWeatherMap::class.java)
            txtCity.text = String.format("%s%s",openWeatherMap.name, openWeatherMap.sys.country)
            txtLastUpdate.text = String.format("Last Updated: %s", common.getDateNow())
            txtDescription.text = String.format("%s", openWeatherMap.weatherList.get(0).description)
            txtHumidity.text = String.format("%d%%", openWeatherMap.main.humidity)
            txtTime.text = String.format("%s/%s", common.unixTimeStampToDateTime(openWeatherMap.sys.sunrise), common.unixTimeStampToDateTime(openWeatherMap.sys.sunset))
            txtCelcius.text = String.format("%.2f °C", openWeatherMap.main.temp)
            picasso.load(common.getImage(openWeatherMap.weatherList.get(0).icon))
                .into(imageView)

        }
    }
}