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
    @StringRes val description: Int = 0
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

        // ---- Faculty of Sciences ----
        R.string.dept_physics to DepartmentInfo(R.string.dept_physics_desc, listOf(
            Specialty(R.string.sp_materials_physics),
            Specialty(R.string.sp_energy_physics),
            Specialty(R.string.sp_medical_physics))),
        R.string.dept_mathematics to DepartmentInfo(R.string.dept_mathematics_desc, listOf(
            Specialty(R.string.sp_math_analysis),
            Specialty(R.string.sp_applied_mathematics),
            Specialty(R.string.sp_data_science),
            Specialty(R.string.sp_cryptography))),
        R.string.dept_chemistry to DepartmentInfo(R.string.dept_chemistry_desc, listOf(
            Specialty(R.string.sp_materials_chemistry),
            Specialty(R.string.sp_analytical_chemistry),
            Specialty(R.string.sp_organic_chemistry))),
        R.string.dept_biology to DepartmentInfo(R.string.dept_biology_desc, listOf(
            Specialty(R.string.sp_biochemistry),
            Specialty(R.string.sp_molecular_biology),
            Specialty(R.string.sp_animal_biology))),
        R.string.dept_agronomy to DepartmentInfo(R.string.dept_agronomy_desc, listOf(
            Specialty(R.string.sp_plant_protection),
            Specialty(R.string.sp_plant_production),
            Specialty(R.string.sp_animal_production))),
        R.string.dept_staps to DepartmentInfo(R.string.dept_staps_desc, listOf(
            Specialty(R.string.sp_education_motor),
            Specialty(R.string.sp_competitive_training),
            Specialty(R.string.sp_elite_training))),
        R.string.dept_computer_science to DepartmentInfo(R.string.department_about, listOf(
            Specialty(R.string.spec_software_engineering, R.string.spec_software_engineering_desc),
            Specialty(R.string.spec_information_systems, R.string.spec_information_systems_desc),
            Specialty(R.string.spec_ai, R.string.spec_ai_desc))),

        // ---- Faculty of Technology ----
        R.string.dept_mechanical to DepartmentInfo(R.string.dept_mechanical_desc, listOf(
            Specialty(R.string.sp_mech_construction),
            Specialty(R.string.sp_energetics),
            Specialty(R.string.sp_automotive))),
        R.string.dept_civil to DepartmentInfo(R.string.dept_civil_desc, listOf(
            Specialty(R.string.sp_geotechnics),
            Specialty(R.string.sp_structures),
            Specialty(R.string.sp_civil_materials))),
        R.string.dept_electrical to DepartmentInfo(R.string.dept_electrical_desc, listOf(
            Specialty(R.string.sp_electrical_eng),
            Specialty(R.string.sp_electrical_machines),
            Specialty(R.string.sp_industrial_automation))),
        R.string.dept_process to DepartmentInfo(R.string.dept_process_desc, listOf(
            Specialty(R.string.sp_chemical_eng),
            Specialty(R.string.sp_polymer_eng),
            Specialty(R.string.sp_water_eng))),

        // ---- Faculty of Hydrocarbons & Chemistry ----
        R.string.dept_earthquake to DepartmentInfo(R.string.dept_earthquake_desc, listOf(
            Specialty(R.string.sp_seismic_geophysics),
            Specialty(R.string.sp_petroleum_geophysics))),
        R.string.dept_transport to DepartmentInfo(R.string.dept_transport_desc, listOf(
            Specialty(R.string.sp_hydro_transport),
            Specialty(R.string.sp_petrochem_mechanics),
            Specialty(R.string.sp_oilfield_mechanics))),
        R.string.dept_chem_pharma to DepartmentInfo(R.string.dept_chem_pharma_desc, listOf(
            Specialty(R.string.sp_refining),
            Specialty(R.string.sp_petrochemistry),
            Specialty(R.string.sp_hse))),
        R.string.dept_automation to DepartmentInfo(R.string.dept_automation_desc, listOf(
            Specialty(R.string.sp_automatic_control),
            Specialty(R.string.sp_industrial_electricity),
            Specialty(R.string.sp_petrochem_instrumentation))),
        R.string.dept_mining to DepartmentInfo(R.string.dept_mining_desc, listOf(
            Specialty(R.string.sp_hydro_production),
            Specialty(R.string.sp_well_drilling),
            Specialty(R.string.sp_mineral_resources))),
        R.string.dept_hydro_economics to DepartmentInfo(R.string.dept_hydro_economics_desc, listOf(
            Specialty(R.string.sp_petroleum_economics),
            Specialty(R.string.sp_hydro_economics_spec))),

        // ---- Faculty of Law & Political Science ----
        R.string.dept_public_law to DepartmentInfo(R.string.dept_public_law_desc, listOf(
            Specialty(R.string.sp_public_law),
            Specialty(R.string.sp_local_admin))),
        R.string.dept_private_law to DepartmentInfo(R.string.dept_private_law_desc, listOf(
            Specialty(R.string.sp_private_law),
            Specialty(R.string.sp_business_law))),
        R.string.dept_political_science to DepartmentInfo(R.string.dept_political_science_desc, listOf(
            Specialty(R.string.sp_international_relations),
            Specialty(R.string.sp_political_orgs),
            Specialty(R.string.sp_international_cooperation))),

        // ---- Faculty of Letters & Languages ----
        R.string.dept_arabic to DepartmentInfo(R.string.dept_arabic_desc, listOf(
            Specialty(R.string.sp_criticism),
            Specialty(R.string.sp_general_linguistics),
            Specialty(R.string.sp_arabic_literature))),
        R.string.dept_french to DepartmentInfo(R.string.dept_french_desc, listOf(
            Specialty(R.string.sp_french_language),
            Specialty(R.string.sp_literature_civ),
            Specialty(R.string.sp_language_sciences))),
        R.string.dept_english to DepartmentInfo(R.string.dept_english_desc, listOf(
            Specialty(R.string.sp_english_language),
            Specialty(R.string.sp_literature_civ),
            Specialty(R.string.sp_language_sciences))),

        // ---- Faculty of Economics & Management ----
        R.string.dept_management to DepartmentInfo(R.string.dept_management_desc, listOf(
            Specialty(R.string.sp_entrepreneurship),
            Specialty(R.string.sp_management),
            Specialty(R.string.sp_financial_management))),
        R.string.dept_commercial to DepartmentInfo(R.string.dept_commercial_desc, listOf(
            Specialty(R.string.sp_marketing),
            Specialty(R.string.sp_digital_marketing),
            Specialty(R.string.sp_intl_trade_finance))),
        R.string.dept_economics to DepartmentInfo(R.string.dept_economics_desc, listOf(
            Specialty(R.string.sp_quantitative_economics),
            Specialty(R.string.sp_monetary_economics),
            Specialty(R.string.sp_business_economics))),
        R.string.dept_finance to DepartmentInfo(R.string.dept_finance_desc, listOf(
            Specialty(R.string.sp_accounting),
            Specialty(R.string.sp_finance),
            Specialty(R.string.sp_accounting_audit)))
    )
    const val UNIVERSITY_MAP_QUERY = "Université M'Hamed Bougara de Boumerdès"

    val campuses: List<Campus> = listOf(
        Campus(R.string.campus_south, "Faculté des Sciences UMBB Boumerdès"),
        Campus(R.string.campus_west, "Faculté de Technologie UMBB Boumerdès"),
        Campus(R.string.campus_north, "Faculté des Hydrocarbures et de la Chimie Boumerdès"),
        Campus(R.string.campus_boudouaou, "Faculté de Droit Boudouaou")
    )
}