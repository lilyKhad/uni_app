package com.example.uniapp

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.bottomnavigation.BottomNavigationView

class DepartmentDetailsActivity : AppCompatActivity() {

    private lateinit var faculty: Faculty
    private var departmentName = 0
    private lateinit var nav: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Apply the saved theme before the screen is drawn
        val prefs = getSharedPreferences("settings", Context.MODE_PRIVATE)
        AppCompatDelegate.setDefaultNightMode(
            prefs.getInt("night_mode", AppCompatDelegate.MODE_NIGHT_NO)
        )

        // Extras sent by FacultiesActivity / FacultyDetailsActivity
        val found = UmbbData.faculties.getOrNull(intent.getIntExtra("faculty_index", -1))
        departmentName = intent.getIntExtra("department_name", 0)
        if (found == null || departmentName == 0) {
            finish()
            return
        }
        faculty = found

        setContentView(R.layout.activity_department_details)

        bindDepartment()
        setupTopBar(prefs)
        setupContactButtons()
        setupSpecialties()
        setupMapButton()
        setupBottomNav()
    }

    override fun onResume() {
        super.onResume()
        if (::nav.isInitialized) {
            nav.menu.findItem(R.id.nav_faculties).isChecked = true
        }
    }

    private fun bindDepartment() {
        val info = UmbbData.departmentInfo[departmentName]

        findViewById<TextView>(R.id.tvFacultyLabel).text =
            getString(R.string.faculty_label) + " • " + getString(faculty.name).uppercase()
        findViewById<TextView>(R.id.tvDepartmentName).setText(departmentName)
        findViewById<TextView>(R.id.tvDepartmentSubtitle).setText(R.string.department_subtitle)
        findViewById<TextView>(R.id.tvAboutText)
            .setText(info?.description ?: R.string.department_default_description)
    }

    private fun setupTopBar(prefs: SharedPreferences) {
        findViewById<View>(R.id.btnBack).setOnClickListener { finish() }

        findViewById<View>(R.id.btnThemeToggle).setOnClickListener {
            val isDark = AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES
            val newMode = if (isDark) AppCompatDelegate.MODE_NIGHT_NO else AppCompatDelegate.MODE_NIGHT_YES
            prefs.edit().putInt("night_mode", newMode).apply()
            AppCompatDelegate.setDefaultNightMode(newMode)
        }

        LanguageHelper.setup(this)
    }

    // A department has no contact of its own: we use its faculty's contact
    private fun setupContactButtons() {
        findViewById<View>(R.id.btnCall).setOnClickListener {
            if (faculty.phone.isBlank()) showNoContact()
            else launch(Intent(Intent.ACTION_DIAL, Uri.parse("tel:${faculty.phone}")))
        }
        findViewById<View>(R.id.btnSms).setOnClickListener {
            if (faculty.phone.isBlank()) showNoContact()
            else launch(Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:${faculty.phone}")))
        }
        findViewById<View>(R.id.btnEmail).setOnClickListener {
            if (faculty.email.isBlank()) showNoContact()
            else launch(Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:${faculty.email}")))
        }
    }

    private fun setupSpecialties() {
        val section = findViewById<View>(R.id.specialtiesSection)
        val container = findViewById<LinearLayout>(R.id.specialtiesContainer)
        val specialties = UmbbData.departmentInfo[departmentName]?.specialties.orEmpty()

        // No specialties for this department: hide the whole section
        if (specialties.isEmpty()) {
            section.visibility = View.GONE
            return
        }

        val inflater = LayoutInflater.from(this)
        specialties.forEach { specialty ->
            val row = inflater.inflate(R.layout.item_specialty, container, false)
            row.findViewById<TextView>(R.id.tvSpecialtyName).setText(specialty.name)
            val descView = row.findViewById<TextView>(R.id.tvSpecialtyDesc)
            if (specialty.description == 0) descView.visibility = View.GONE
            else descView.setText(specialty.description)
            container.addView(row)
        }
    }

    private fun setupMapButton() {
        val query = UmbbData.campuses.find { it.name == faculty.campus }?.mapQuery
            ?: UmbbData.UNIVERSITY_MAP_QUERY
        findViewById<View>(R.id.btnMaps).setOnClickListener { MapHelper.open(this, query) }
    }

    private fun setupBottomNav() {
        nav = findViewById(R.id.bottom_nav)
        nav.selectedItemId = R.id.nav_faculties
        nav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_discover -> openScreen(DiscoverActivity::class.java)
                R.id.nav_location -> openScreen(LocationActivity::class.java)
                R.id.nav_faculties -> finish()
            }
            true
        }
    }

    private fun openScreen(screen: Class<*>) {
        startActivity(Intent(this, screen).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT))
        overridePendingTransition(0, 0)
    }

    private fun launch(intent: Intent) {
        try {
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(this, R.string.no_app_available, Toast.LENGTH_SHORT).show()
        }
    }

    private fun showNoContact() {
        Toast.makeText(this, R.string.no_contact_info, Toast.LENGTH_SHORT).show()
    }
}