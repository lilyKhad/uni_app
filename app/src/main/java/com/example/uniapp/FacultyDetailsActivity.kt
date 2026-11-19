package com.example.uniapp

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
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

class FacultyDetailsActivity : AppCompatActivity() {

    private var facultyIndex = -1
    private lateinit var faculty: Faculty
    private lateinit var nav: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Apply the saved theme before the screen is drawn (same as DiscoverActivity)
        val prefs = getSharedPreferences("settings", Context.MODE_PRIVATE)
        AppCompatDelegate.setDefaultNightMode(
            prefs.getInt("night_mode", AppCompatDelegate.MODE_NIGHT_NO)
        )

        // Faculty chosen in FacultiesActivity
        facultyIndex = intent.getIntExtra("faculty_index", -1)
        val found = UmbbData.faculties.getOrNull(facultyIndex)
        if (found == null) {
            finish()
            return
        }
        faculty = found

        setContentView(R.layout.activity_faculty_details)

        bindFaculty()
        setupTopBar(prefs)
        setupContactButtons()
        setupDepartments()
        setupMapButton()
        setupBottomNav()
    }

    override fun onResume() {
        super.onResume()
        if (::nav.isInitialized) {
            nav.menu.findItem(R.id.nav_faculties).isChecked = true
        }
    }

    private fun bindFaculty() {
        findViewById<TextView>(R.id.tvFacultyName).setText(faculty.name)
        findViewById<TextView>(R.id.tvFacultyDesc).setText(faculty.description)
        findViewById<TextView>(R.id.tvAboutText).setText(faculty.description)
    }

    private fun setupTopBar(prefs: android.content.SharedPreferences) {
        findViewById<View>(R.id.btnBack).setOnClickListener { finish() }

        findViewById<View>(R.id.btnThemeToggle).setOnClickListener {
            val isDark = AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES
            val newMode = if (isDark) AppCompatDelegate.MODE_NIGHT_NO else AppCompatDelegate.MODE_NIGHT_YES
            prefs.edit().putInt("night_mode", newMode).apply()
            AppCompatDelegate.setDefaultNightMode(newMode)
        }

        LanguageHelper.setup(this)
    }

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

    private fun setupDepartments() {
        val container = findViewById<LinearLayout>(R.id.departmentsContainer)
        val inflater = LayoutInflater.from(this)

        faculty.departments.forEachIndexed { index, departmentName ->
            val row = inflater.inflate(R.layout.item_faculty_department, container, false)
            row.findViewById<TextView>(R.id.tvDeptNumber).text = String.format("%02d", index + 1)
            row.findViewById<TextView>(R.id.tvDeptName).setText(departmentName)
            row.setOnClickListener { openDepartment(departmentName) }
            container.addView(row)
        }
    }

    private fun setupMapButton() {
        // Uses the campus of this faculty, like LocationActivity does
        val query = UmbbData.campuses.find { it.name == faculty.campus }?.mapQuery
            ?: UmbbData.UNIVERSITY_MAP_QUERY

        findViewById<View>(R.id.btnMaps).setOnClickListener {
            MapHelper.open(this, query)
        }
    }

    private fun setupBottomNav() {
        nav = findViewById(R.id.bottom_nav)
        nav.selectedItemId = R.id.nav_faculties
        nav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_discover -> openScreen(DiscoverActivity::class.java)
                R.id.nav_location -> openScreen(LocationActivity::class.java)
                R.id.nav_faculties -> finish() // Faculties is the previous screen
            }
            true
        }
    }

    private fun openDepartment(departmentName: Int) {
        val intent = Intent(this, DepartmentDetailsActivity::class.java)
        intent.putExtra("faculty_index", facultyIndex)
        intent.putExtra("department_name", departmentName)
        startActivity(intent)
    }

    private fun openScreen(screen: Class<*>) {
        startActivity(Intent(this, screen).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT))
        overridePendingTransition(0, 0)
    }

    // Trying to start the activity directly is more reliable than resolveActivity on Android 11+
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