package com.lazuardy.zonacoding

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class ContactUsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_us)
    }

    fun openFacebook(view: View){
        openWebsite("https://facebook.com/zonacoding")
    }

    fun openTwitter(view: View){
        openWebsite("https://twitter.com/zonacoding")
    }

    fun openGithub(view: View){
        openWebsite("https://github.com/zonacodingtech")
    }

    fun openInstagram(view: View){
        openWebsite("https://www.instagram.com/zonacoding")
    }

    private fun openWebsite(url: String){
        val webpage: Uri = Uri.parse(url)
        val intent = Intent(Intent.ACTION_VIEW, webpage)
        startActivity(intent);
    }
}