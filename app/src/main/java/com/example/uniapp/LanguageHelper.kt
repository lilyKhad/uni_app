package com.example.uniapp

import android.app.Activity
import android.graphics.Typeface
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.core.os.LocaleListCompat

object LanguageHelper {

    fun setup(activity: Activity) {
        val en = activity.findViewById<TextView>(R.id.tvLangEn)
        val ar = activity.findViewById<TextView>(R.id.tvLangAr)

        val isArabic = activity.resources.configuration.locales[0].language == "ar"
        highlight(activity, en, !isArabic)
        highlight(activity, ar, isArabic)

        en.setOnClickListener { if (isArabic) setLanguage("en") }
        ar.setOnClickListener { if (!isArabic) setLanguage("ar") }
    }

    // Changes the app language and recreates the open screens automatically
    private fun setLanguage(tag: String) {
        AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(tag))
    }

    private fun highlight(activity: Activity, tv: TextView, selected: Boolean) {
        val color = if (selected) R.color.umbb_primary else R.color.umbb_text_secondary
        tv.setTextColor(ContextCompat.getColor(activity, color))
        tv.setTypeface(null, if (selected) Typeface.BOLD else Typeface.NORMAL)
    }
}