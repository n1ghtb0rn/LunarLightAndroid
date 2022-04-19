package com.danielfalkedal.lunarlight.Utils

import android.util.Log

class Encryption {

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