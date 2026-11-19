package com.example.uniapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.bottomnavigation.BottomNavigationView

class DiscoverActivity : AppCompatActivity() {

    private lateinit var nav: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Apply the saved theme before the screen is drawn
        val prefs = getSharedPreferences("settings", Context.MODE_PRIVATE)
        AppCompatDelegate.setDefaultNightMode(
            prefs.getInt("night_mode", AppCompatDelegate.MODE_NIGHT_NO)
        )

        setContentView(R.layout.activity_discover)

        // Theme toggle
        findViewById<View>(R.id.btnThemeToggle).setOnClickListener {
            val isDark = AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES
            val newMode = if (isDark) AppCompatDelegate.MODE_NIGHT_NO else AppCompatDelegate.MODE_NIGHT_YES
            prefs.edit().putInt("night_mode", newMode).apply()
            AppCompatDelegate.setDefaultNightMode(newMode)
        }

        // EN / العربية switch
        LanguageHelper.setup(this)

        // Search bar and cards
        findViewById<View>(R.id.cardSearch).setOnClickListener {
            openScreen(FacultiesActivity::class.java)
        }
        findViewById<View>(R.id.cardFaculties).setOnClickListener {
            openScreen(FacultiesActivity::class.java)
        }
        findViewById<View>(R.id.cardDepartments).setOnClickListener {
            openScreen(FacultiesActivity::class.java, showDepartments = true)
        }

        // "View map" opens the Location screen, same as the Location tab
        findViewById<View>(R.id.tvViewMap).setOnClickListener {
            openScreen(LocationActivity::class.java)
        }

        // Bottom navigation
        nav = findViewById(R.id.bottom_nav)
        nav.selectedItemId = R.id.nav_discover
        nav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_faculties -> openScreen(FacultiesActivity::class.java)
                R.id.nav_location -> openScreen(LocationActivity::class.java)
            }
            true
        }
    }

    override fun onResume() {
        super.onResume()
        // Keep "Discover" highlighted when coming back to this screen
        nav.menu.findItem(R.id.nav_discover).isChecked = true
    }

    private fun openScreen(screen: Class<*>, showDepartments: Boolean = false) {
        val intent = Intent(this, screen).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
        if (showDepartments) intent.putExtra(FacultiesActivity.EXTRA_SHOW_DEPARTMENTS, true)
        startActivity(intent)
        overridePendingTransition(0, 0)
    }
}