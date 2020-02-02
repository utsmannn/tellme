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

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_exception.*
import kotlinx.android.synthetic.main.dialog_stacktrace.view.*

class ExceptionActivity : TellmeActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exception)
        supportActionBar?.hide()

        val colorAccent = intent.getIntExtra("color_accent", Color.parseColor("#b77b7f"))

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val w = window
            w.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val decor = window.decorView
            decor.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }

        val message = getMessageError()
        val stacktrace = getStackTrace()
        val mails = intent.getStringArrayExtra("mail") ?: emptyArray()

        text_message.text = message
        img_snap.setColorFilter(colorAccent, PorterDuff.Mode.MULTIPLY)

        btn_ok.setCardBackgroundColor(colorAccent)
        btn_detail.setCardBackgroundColor(colorAccent)

        btn_ok.setOnClickListener {
            finish()
        }

        val view = LayoutInflater.from(this).inflate(R.layout.dialog_stacktrace, null)
        val alertDialog = AlertDialog.Builder(this)
            .setView(view)

        view.text_error.text = stacktrace
        view.btn_report.setCardBackgroundColor(colorAccent)
        view.btn_report.setOnClickListener {

            if (mails.isNotEmpty()) {
                val intent = Intent(Intent.ACTION_SENDTO).apply {
                    data = Uri.parse("mailto:")
                    putExtra(Intent.EXTRA_EMAIL, mails)
                    putExtra(Intent.EXTRA_SUBJECT, "Error Report")
                }

                if (intent.resolveActivity(packageManager) != null) {
                    startActivity(intent)
                    finish()
                }
            } else {
                Toast.makeText(this, "Email address not set", Toast.LENGTH_SHORT).show()
            }
        }

        val dialog = alertDialog.create()

        btn_detail.setOnClickListener {
            dialog.show()
        }
    }
}