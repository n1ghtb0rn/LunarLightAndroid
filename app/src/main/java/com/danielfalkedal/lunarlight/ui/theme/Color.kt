package com.danielfalkedal.lunarlight.ui.theme

import android.util.Log
import androidx.compose.ui.graphics.Color
import com.danielfalkedal.lunarlight.Documents.User
import com.danielfalkedal.lunarlight.Utils.LocalData

val Purple200 = Color(0xFFBB86FC)
val Purple500 = Color(0xFF6200EE)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF03DAC5)

val FullTransparent = Color(0, 0, 0, 0)
val WhiteTransparent = Color(255,255,255, 50)
val BlackTransparent = Color(0, 0, 0, 75)

val PrimaryVariantColor = Color(0,0,0, 125)
val PrimaryColor = Color(0,0,0, 100) //eg. Button Background
val SecondaryColor = Color(255,255,255, 150)

val Aquarius = Color(159, 236, 255, 255)
val Aries = Color(250, 170, 61, 255)
val Cancer = Color(255, 85, 0, 255)
val Capricorn = Color(236, 32, 60, 255)
val Gemeni = Color(255, 246, 0, 255)
val Leo = Color(46, 94, 248, 255)
val Libra = Color(255, 152, 232, 255)
val Pisces = Color(41, 229, 240, 255)
val Sagittarius = Color(255, 249, 146, 255)
val Scorpio = Color(73, 228, 66, 255)
val Taurus = Color(130, 255, 142, 255)
val Virgo = Color(255, 177, 165, 255)

fun getUserBackgroundColor(month: Long, day: Long): Color {
    val colorIndex = User.getStoneIndex(month.toInt(), day.toInt())
    val colorName = LocalData.profileBackground[colorIndex]
    val color = getColorByUser(null, colorName)
    return color
}

fun getColorByUser(user: User? = null, string: String? = null): Color {

    var returnColor = Color.White

    var colorName = ""

    if (user != null ) {
        val colorIndex = User.getStoneIndex(user.month.toInt(), user.day.toInt())
        colorName = LocalData.profileBackground[colorIndex]
    }
    else if (string != null) {
        colorName = string
    }

    when (colorName) {


        "Aquarius" -> returnColor = Aquarius

        "Aries" -> returnColor = Aries

        "Cancer" -> returnColor = Cancer

        "Capricorn" -> returnColor = Capricorn

        "Gemeni" -> returnColor = Gemeni

        "Leo"	 -> returnColor = Leo

        "Libra" -> returnColor = Libra

        "Pisces" -> returnColor = Pisces

        "Sagittarius" -> returnColor = Sagittarius

        "Scorpio" -> returnColor = Scorpio

        "Taurus" -> returnColor = Taurus

        "Virgo" -> returnColor = Virgo


    }

    return returnColor

}