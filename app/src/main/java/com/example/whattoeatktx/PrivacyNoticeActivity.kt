package com.example.whattoeatktx


import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity


class PrivacyNoticeActivity : AppCompatActivity() {
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_privacy_notice)
        /**
         * https://stackoverflow.com/questions/5089578/enabling-general-javascript-in-webviewclient#:~:text=The%20proper%20way%20to%20enable%20JavaScript%20is%20by,not%20working%20then%20try%20adding%20the%20below%20line.
         */
        val vistaWeb = findViewById<WebView>(R.id.webView)
        vistaWeb.clearCache(true)
        vistaWeb.clearHistory()
        vistaWeb.settings.javaScriptEnabled = true
        vistaWeb.settings.javaScriptCanOpenWindowsAutomatically = true
        vistaWeb.loadUrl("file:///android_asset/web/index.html")
    }
}