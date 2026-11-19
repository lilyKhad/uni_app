package com.example.uniapp

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast

object MapHelper {

    // Opens the Google Maps app, or the browser if Maps isn't installed
    fun open(context: Context, query: String) {
        val uri = Uri.parse(
            "https://www.google.com/maps/search/?api=1&query=" + Uri.encode(query)
        )
        try {
            context.startActivity(Intent(Intent.ACTION_VIEW, uri))
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(context, R.string.no_map_app, Toast.LENGTH_SHORT).show()
        }
    }
}