package com.alexjanci.niceweatherwerehaving.Model

class OpenWeatherMap {
    var coord: Coord = Coord()
    var weatherList: List<Weather> = listOf()
    var base: String = ""
    var main: Main = Main()
    var wind: Wind = Wind()
    var rain: Rain = Rain()
    var clouds: Clouds = Clouds()
    var dt: Int = 0
    var sys: Sys = Sys()
    var id: Int = 0
    var name: String = ""
    var cod: Int = 0


}