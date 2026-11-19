package com.example.uniapp

import androidx.annotation.StringRes

data class Faculty(
    @StringRes val name: Int,
    @StringRes val campus: Int,
    val departments: List<Int>
)

data class Department(
    @StringRes val name: Int,
    @StringRes val facultyName: Int,
    val facultyIndex: Int
)
data class Campus(
    @StringRes val name: Int,
    val mapQuery: String
)
object UmbbData {

    val faculties: List<Faculty> = listOf(
        Faculty(
            R.string.faculty_sciences, R.string.campus_south,
            listOf(
                R.string.dept_physics, R.string.dept_mathematics, R.string.dept_chemistry,
                R.string.dept_biology, R.string.dept_agronomy, R.string.dept_staps,
                R.string.dept_computer_science
            )
        ),
        Faculty(
            R.string.faculty_technology, R.string.campus_west,
            listOf(
                R.string.dept_mechanical, R.string.dept_civil,
                R.string.dept_electrical, R.string.dept_process
            )
        ),
        Faculty(
            R.string.faculty_hydrocarbons, R.string.campus_north,
            listOf(
                R.string.dept_earthquake, R.string.dept_transport, R.string.dept_chem_pharma,
                R.string.dept_automation, R.string.dept_mining, R.string.dept_hydro_economics
            )
        ),
        Faculty(
            R.string.faculty_law, R.string.campus_boudouaou,
            listOf(R.string.dept_public_law, R.string.dept_private_law, R.string.dept_political_science)
        ),
        Faculty(
            R.string.faculty_letters, R.string.campus_boudouaou,
            listOf(R.string.dept_arabic, R.string.dept_french, R.string.dept_english)
        ),
        Faculty(
            R.string.faculty_economics, R.string.campus_north,
            listOf(
                R.string.dept_management, R.string.dept_commercial,
                R.string.dept_economics, R.string.dept_finance
            )
        )
    )

    // Every department, together with the faculty it belongs to
    val departments: List<Department> =
        faculties.flatMapIndexed { index, faculty ->
            faculty.departments.map { Department(it, faculty.name, index) }
        }
    const val UNIVERSITY_MAP_QUERY = "Université M'Hamed Bougara de Boumerdès"

    val campuses: List<Campus> = listOf(
        Campus(R.string.campus_south, "Faculté des Sciences UMBB Boumerdès"),
        Campus(R.string.campus_west, "Faculté de Technologie UMBB Boumerdès"),
        Campus(R.string.campus_north, "Faculté des Hydrocarbures et de la Chimie Boumerdès"),
        Campus(R.string.campus_boudouaou, "Faculté de Droit Boudouaou")
    )
}