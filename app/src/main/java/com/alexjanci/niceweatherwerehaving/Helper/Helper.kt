package com.alexjanci.niceweatherwerehaving.Helper

import android.util.Log
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.lang.Exception
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

class Helper {
    var stream: String = ""
    var code = 0

    fun getHTTPData(urlString: String): String {
        try {
            stream = URL(urlString).readText()
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: Exception){
            "null"
        }
        return stream
    }
}