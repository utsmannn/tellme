/*
 * Copyright 2020 Muhammad Utsman
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.utsman.tellme

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.core.content.ContextCompat
import java.io.PrintWriter
import java.io.StringWriter
import kotlin.system.exitProcess

class Tellme private constructor(private val context: Context): Thread.UncaughtExceptionHandler {

    private var handler: Thread.UncaughtExceptionHandler? = null
    private var customExceptionActivity: String? = null
    private var colorAccent: Int? = null
    private var email: Array<String> = arrayOf()

    companion object {
        fun setInstance(context: Context): Tellme {
            return Tellme(context)
        }
    }

    init {
        handler = Thread.getDefaultUncaughtExceptionHandler()
        Thread.setDefaultUncaughtExceptionHandler(this)
    }

    fun setColorAccent(colorAccent: Int): Tellme {
        this.colorAccent = ContextCompat.getColor(context, colorAccent)
        return this
    }

    fun setEmail(vararg email: String): Tellme {
        this.email = email as Array<String>
        return this
    }

    fun withActivity(clazz: Class<*>) {
        customExceptionActivity = clazz.name
    }

    override fun uncaughtException(t: Thread, e: Throwable) {
        if (e is Exception) {
            val stringWriter = StringWriter()
            val printerWriter = PrintWriter(stringWriter)
            e.printStackTrace(printerWriter)
            val stackError = stringWriter.toString()

            val intent = if (customExceptionActivity != null) {
                Intent(context, Class.forName(customExceptionActivity!!))
            } else {
                Intent(context, ExceptionActivity::class.java)
            }.apply {
                putExtra("stacktrace", stackError)
                putExtra("message", e.message)
                putExtra("color_accent", colorAccent)
                putExtra("mail", email)

                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT)
                }
            }

            context.startActivity(intent)

            exitProcess(0)
        } else {
            handler?.uncaughtException(t, e)
        }
    }

}