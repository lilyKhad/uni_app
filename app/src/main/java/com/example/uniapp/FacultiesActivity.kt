package com.example.uniapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView

class FacultiesActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_SHOW_DEPARTMENTS = "show_departments"
        private const val KEY_SHOW_DEPARTMENTS = "key_show_departments"
    }

    private var showingFaculties = true
    private lateinit var facultyAdapter: FacultyAdapter
    private lateinit var departmentAdapter: DepartmentAdapter
    private lateinit var rv: RecyclerView
    private lateinit var etSearch: EditText
    private lateinit var tabFaculties: TextView
    private lateinit var tabDepartments: TextView
    private lateinit var tvBrowseTitle: TextView
    private lateinit var tvHint: TextView
    private lateinit var nav: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_faculties)

        rv = findViewById(R.id.rvFaculties)
        etSearch = findViewById(R.id.etSearch)
        tabFaculties = findViewById(R.id.tabFaculties)
        tabDepartments = findViewById(R.id.tabDepartments)
        tvBrowseTitle = findViewById(R.id.tvBrowseTitle)
        tvHint = findViewById(R.id.tvHint)

        // Lists
        facultyAdapter = FacultyAdapter(emptyList()) { openFaculty(it) }
        departmentAdapter = DepartmentAdapter(emptyList()) { openDepartment(it) }
        rv.layoutManager = GridLayoutManager(this, 2)

        // Search
        etSearch.doAfterTextChanged { refreshList() }

        // Tabs
        tabFaculties.setOnClickListener { selectTab(true) }
        tabDepartments.setOnClickListener { selectTab(false) }

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

        // Bottom navigation
        nav = findViewById(R.id.bottom_nav)
        nav.selectedItemId = R.id.nav_faculties
        nav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_discover -> openScreen(DiscoverActivity::class.java)
                R.id.nav_location -> openScreen(LocationActivity::class.java)
            }
            true
        }

        // Open on the right tab (also keeps the tab after a language/theme change)
        val showDepartments = savedInstanceState?.getBoolean(KEY_SHOW_DEPARTMENTS)
            ?: intent.getBooleanExtra(EXTRA_SHOW_DEPARTMENTS, false)
        selectTab(!showDepartments)
    }

    // Called when this screen is already open and is brought back to the front
    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        selectTab(!intent.getBooleanExtra(EXTRA_SHOW_DEPARTMENTS, false))
    }

    override fun onResume() {
        super.onResume()
        nav.menu.findItem(R.id.nav_faculties).isChecked = true
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(KEY_SHOW_DEPARTMENTS, !showingFaculties)
    }

    private fun selectTab(faculties: Boolean) {
        showingFaculties = faculties

        val primary = ContextCompat.getColor(this, R.color.umbb_text_primary)
        val secondary = ContextCompat.getColor(this, R.color.umbb_text_secondary)

        // New layout manager each time, so the list height is recalculated from zero
        rv.layoutManager = GridLayoutManager(this, if (faculties) 2 else 1)

        if (faculties) {
            tabFaculties.setBackgroundResource(R.drawable.bg_tab_selected)
            tabDepartments.background = null
            tabFaculties.setTextColor(primary)
            tabDepartments.setTextColor(secondary)
            tvBrowseTitle.setText(R.string.browse_faculties)
            tvHint.setText(R.string.choose_faculty_hint)
            rv.adapter = facultyAdapter
        } else {
            tabDepartments.setBackgroundResource(R.drawable.bg_tab_selected)
            tabFaculties.background = null
            tabDepartments.setTextColor(primary)
            tabFaculties.setTextColor(secondary)
            tvBrowseTitle.setText(R.string.browse_departments)
            tvHint.setText(R.string.choose_department_hint)
            rv.adapter = departmentAdapter
        }

        refreshList()
    }

    // Filters the list by the search text (works in English and Arabic)
    // Filters the list by the search text (works in English and Arabic)
    private fun refreshList() {
        val query = etSearch.text.toString().trim()
        if (showingFaculties) {
            facultyAdapter.update(
                UmbbData.faculties.filter { getString(it.name).contains(query, ignoreCase = true) }
            )
        } else {
            departmentAdapter.update(
                UmbbData.departments.filter {
                    getString(it.name).contains(query, ignoreCase = true) ||
                            getString(it.facultyName).contains(query, ignoreCase = true)
                }
            )
        }
    }

    private fun openFaculty(faculty: Faculty) {
        val intent = Intent(this, FacultyDetailsActivity::class.java)
        intent.putExtra("faculty_index", UmbbData.faculties.indexOf(faculty))
        startActivity(intent)
    }

    private fun openDepartment(department: Department) {
        val intent = Intent(this, DepartmentDetailsActivity::class.java)
        intent.putExtra("faculty_index", department.facultyIndex)
        intent.putExtra("department_name", department.name) // string resource id
        startActivity(intent)
    }

    private fun openScreen(screen: Class<*>) {
        startActivity(Intent(this, screen).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT))
        overridePendingTransition(0, 0)
    }
}