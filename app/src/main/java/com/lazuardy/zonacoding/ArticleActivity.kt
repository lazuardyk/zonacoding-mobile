package com.lazuardy.zonacoding

import android.os.Bundle
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.activity_article.*
import kotlinx.android.synthetic.main.activity_main.*

class ArticleActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article)
        showLoading(true)

        val articleUrl = intent.extras?.get("article_url") as String
        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                view?.loadUrl("javascript:var header = document.getElementById('navbarArticle').style.display='none'");
                showLoading(false)
            }
        }
        webView.loadUrl(articleUrl)
        webView.settings.javaScriptEnabled = true
        webView.settings.setSupportZoom(true)
        webView.settings.domStorageEnabled = true
    }

    override fun onBackPressed() {
        if (webView.canGoBack())
            webView.goBack()
        else
            super.onBackPressed()
    }

    private fun showLoading(loading: Boolean) {
        when(loading) {
            true -> progressBarArticle.visibility = View.VISIBLE
            false -> progressBarArticle.visibility = View.GONE
        }
    }
}