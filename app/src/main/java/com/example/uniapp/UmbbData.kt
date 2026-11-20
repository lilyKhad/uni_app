package com.example.uniapp

import androidx.annotation.StringRes

data class Faculty(
    @StringRes val name: Int,
    @StringRes val campus: Int,
    val departments: List<Int>,
    @StringRes val description: Int = R.string.faculty_default_description,
    val phone: String = "",
    val email: String = ""
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
data class Specialty(
    @StringRes val name: Int,
    @StringRes val description: Int
)

data class DepartmentInfo(
    @StringRes val description: Int,
    val specialties: List<Specialty> = emptyList()
)
object UmbbData {

    val faculties: List<Faculty> = listOf(

        Faculty(
            R.string.faculty_sciences, R.string.campus_south,
            listOf(
                R.string.dept_physics, R.string.dept_mathematics, R.string.dept_chemistry,
                R.string.dept_biology, R.string.dept_agronomy, R.string.dept_staps,
                R.string.dept_computer_science
            ),
            description = R.string.faculty_sciences_desc,
            phone = "+213000000000",
            email = "sciences@univ-boumerdes.dz"
        ),

        Faculty(
            R.string.faculty_technology, R.string.campus_west,
            listOf(
                R.string.dept_mechanical, R.string.dept_civil,
                R.string.dept_electrical, R.string.dept_process
            ),
            description = R.string.faculty_technology_desc,
            phone = "+213000000000",
            email = "technology@univ-boumerdes.dz"
        ),

        Faculty(
            R.string.faculty_hydrocarbons, R.string.campus_north,
            listOf(
                R.string.dept_earthquake, R.string.dept_transport, R.string.dept_chem_pharma,
                R.string.dept_automation, R.string.dept_mining, R.string.dept_hydro_economics
            ),
            description = R.string.faculty_hydrocarbons_desc,
            phone = "+213000000000",
            email = "hydrocarbons@univ-boumerdes.dz"
        ),

        Faculty(
            R.string.faculty_law, R.string.campus_boudouaou,
            listOf(
                R.string.dept_public_law,
                R.string.dept_private_law,
                R.string.dept_political_science
            ),
            description = R.string.faculty_law_desc,
            phone = "+213000000000",
            email = "law@univ-boumerdes.dz"
        ),

        Faculty(
            R.string.faculty_letters, R.string.campus_boudouaou,
            listOf(
                R.string.dept_arabic,
                R.string.dept_french,
                R.string.dept_english
            ),
            description = R.string.faculty_letters_desc,
            phone = "+213000000000",
            email = "letters@univ-boumerdes.dz"
        ),

        Faculty(
            R.string.faculty_economics, R.string.campus_north,
            listOf(
                R.string.dept_management, R.string.dept_commercial,
                R.string.dept_economics, R.string.dept_finance
            ),
            description = R.string.faculty_economics_desc,
            phone = "+213000000000",
            email = "economics@univ-boumerdes.dz"
        )
    )

    // Every department, together with the faculty it belongs to
    val departments: List<Department> =
        faculties.flatMapIndexed { index, faculty ->
            faculty.departments.map { Department(it, faculty.name, index) }
        }
    // Details of each department (key = the department name resource id)
    val departmentInfo: Map<Int, DepartmentInfo> = mapOf(
        R.string.dept_computer_science to DepartmentInfo(
            R.string.department_about,
            listOf(
                Specialty(R.string.spec_software_engineering, R.string.spec_software_engineering_desc),
                Specialty(R.string.spec_information_systems, R.string.spec_information_systems_desc),
                Specialty(R.string.spec_ai, R.string.spec_ai_desc)
            )
        ),
        R.string.dept_mathematics to DepartmentInfo(
            R.string.dept_mathematics_desc,
            listOf(
                Specialty(R.string.spec_applied_math, R.string.spec_applied_math_desc),
                Specialty(R.string.spec_statistics, R.string.spec_statistics_desc)
            )
        )
    )
    const val UNIVERSITY_MAP_QUERY = "Université M'Hamed Bougara de Boumerdès"

    val campuses: List<Campus> = listOf(
        Campus(R.string.campus_south, "Faculté des Sciences UMBB Boumerdès"),
        Campus(R.string.campus_west, "Faculté de Technologie UMBB Boumerdès"),
        Campus(R.string.campus_north, "Faculté des Hydrocarbures et de la Chimie Boumerdès"),
        Campus(R.string.campus_boudouaou, "Faculté de Droit Boudouaou")
    )
}