package com.example.uniapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.bottomnavigation.BottomNavigationView

class LocationActivity : AppCompatActivity() {

    private lateinit var nav: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location)

        // Back, theme toggle, language
        findViewById<View>(R.id.btnBack).setOnClickListener { finish() }
        findViewById<View>(R.id.btnThemeToggle).setOnClickListener {
            val prefs = getSharedPreferences("settings", Context.MODE_PRIVATE)
            val isDark = AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES
            val newMode = if (isDark) AppCompatDelegate.MODE_NIGHT_NO else AppCompatDelegate.MODE_NIGHT_YES
            prefs.edit().putInt("night_mode", newMode).apply()
            AppCompatDelegate.setDefaultNightMode(newMode)
        }
        LanguageHelper.setup(this)

        // Whole university on the map
        findViewById<View>(R.id.btnOpenMap).setOnClickListener {
            MapHelper.open(this, UmbbData.UNIVERSITY_MAP_QUERY)
        }

        // One card per campus, showing the faculties located there
        val container = findViewById<LinearLayout>(R.id.campusContainer)
        for (campus in UmbbData.campuses) {
            val card = layoutInflater.inflate(R.layout.item_campus, container, false)
            card.findViewById<TextView>(R.id.tvCampusName).setText(campus.name)
            card.findViewById<TextView>(R.id.tvCampusFaculties).text =
                UmbbData.faculties
                    .filter { it.campus == campus.name }
                    .joinToString(" • ") { getString(it.name) }
            card.setOnClickListener { MapHelper.open(this, campus.mapQuery) }
            container.addView(card)
        }

        // Bottom navigation
        nav = findViewById(R.id.bottom_nav)
        nav.selectedItemId = R.id.nav_location
        nav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_discover -> openScreen(DiscoverActivity::class.java)
                R.id.nav_faculties -> openScreen(FacultiesActivity::class.java)
            }
            true
        }
    }

    override fun onResume() {
        super.onResume()
        nav.menu.findItem(R.id.nav_location).isChecked = true
    }

    private fun openScreen(screen: Class<*>) {
        startActivity(Intent(this, screen).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT))
        overridePendingTransition(0, 0)
    }
}