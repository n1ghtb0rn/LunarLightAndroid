package com.danielfalkedal.lunarlight.Documents

import android.util.Log

data class User(
    val id: String,
    val username: String,
    val password: String,
    val email: String,
    var avatar: String,
    val year: Long,
    val month: Long,
    val day: Long

){
    constructor() : this("", "", "", "", "", 2010, 1, 1)

    companion object {
        fun getStoneIndex(month: Int, day: Int): Int {

            when (month) {

                1 -> {
                    if (day >= 1 && day <= 20) {
                        return 0
                    }
                    else {
                        return 1
                    }
                }

                2 -> {
                    if (day >= 1 && day <= 18) {
                        return 1
                    }
                    else {
                        return 2
                    }
                }

                3 -> {
                    if (day >= 1 && day <= 19) {
                        return 2
                    }
                    else {
                        return 3
                    }
                }

                4 -> {
                    if (day >= 1 && day <= 19) {
                        return 3
                    }
                    else {
                        return 4
                    }
                }

                5 -> {
                    if (day >= 1 && day <= 20) {
                        return 4
                    }
                    else {
                        return 5
                    }
                }

                6 -> {
                    if (day >= 1 && day <= 20) {
                        return 5
                    }
                    else {
                        return 6
                    }
                }

                7 -> {
                    if (day >= 1 && day <= 22) {
                        return 6
                    }
                    else {
                        return 7
                    }
                }

                8 -> {
                    if (day >= 1 && day <= 22) {
                        return 7
                    }
                    else {
                        return 8
                    }
                }

                9 -> {
                    if (day >= 1 && day <= 22) {
                        return 8
                    }
                    else {
                        return 9
                    }
                }

                10 -> {
                    if (day >= 1 && day <= 22) {
                        return 9
                    }
                    else {
                        return 10
                    }
                }

                11 -> {
                    if (day >= 1 && day <= 22) {
                        return 10
                    }
                    else {
                        return 11
                    }
                }

                12 -> {
                    if (day >= 1 && day <= 21) {
                        return 11
                    }
                    else {
                        return 0
                    }
                }

            }

            return 0

        }
    }

}