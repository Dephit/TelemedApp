package com.app.telemed.interfaces

import java.util.*

data class Lesson(
    var date: Calendar = Calendar.getInstance(),
    var passed: Boolean = false,
    var title: String = "",
    val desc: String = "",
)
