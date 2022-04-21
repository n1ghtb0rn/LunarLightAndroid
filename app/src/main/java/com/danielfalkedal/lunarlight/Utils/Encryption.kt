package com.danielfalkedal.lunarlight.Utils

import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.util.Log
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL

class Encryption {

    //Get an encrypted token from user input
    fun getTokenByHttpRequest(input: String): String {
        var token = "error"

        val policy = ThreadPolicy.Builder().permitAll().build()

        StrictMode.setThreadPolicy(policy)

        val url = URL("https://lunarlightkyh.000webhostapp.com/?password=$input")
        val connection = url.openConnection()
        BufferedReader(InputStreamReader(connection.getInputStream())).use { inp ->
            var line: String?
            while (inp.readLine().also { line = it } != null) {
                Log.d("Danne", "$line")
                if (line != null && line is String) {
                    token = line as String
                    break
                }
            }
        }

        return token
    }

    //Old "manual" encryption. Replaced by getTokenByHttpRequest()
    fun getToken(input: String): String {
        var token = ""

        var shuffledInput = input.toMutableList()

        for (n in 0..shuffledInput.size-2 step 2) {
            val char1 = shuffledInput[n]
            val char2 = shuffledInput[n+1]

            shuffledInput[n] = char2
            shuffledInput[n+1] = char1
        }

        var i = 0
        for (char in shuffledInput) {

            var asciiValue: Int = char.code

            if (i % 3 == 0) {
                asciiValue = (asciiValue.toDouble() * 1.1).toInt()
            }
            else if (i % 2 == 0) {
                asciiValue = (asciiValue.toDouble() * 1.2).toInt()
            }
            else {
                asciiValue = (asciiValue.toDouble() * 0.9).toInt()
            }

            while (asciiValue > 127) {
                asciiValue -= 127
            }
            while (asciiValue < 32) {
                asciiValue += 32
            }

            if (i % 3 == 0) {
                token += asciiValue.toChar()
            }
            else if (i % 2 == 0) {
                token += asciiValue.toChar()
            }
            else {
                token += "$asciiValue"
            }

            i++
        }

        return token
    }

}