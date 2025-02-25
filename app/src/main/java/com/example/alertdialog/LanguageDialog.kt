package com.example.alertdialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class LanguageDialog(val ctx: Context, private val onLanguageSelected: (String) -> Unit) : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val sharedPreferences = ctx.getSharedPreferences("AppSettings", Context.MODE_PRIVATE)
        val savedLanguage = sharedPreferences.getString("language", "English") ?: "English"

        val languages = arrayOf("English", "Русский", "Español")
        val selectedLanguageIndex = languages.indexOf(savedLanguage)

        return AlertDialog.Builder(ctx)
            .setTitle("Select Language")
            .setSingleChoiceItems(languages, selectedLanguageIndex) { _, which -> }
            .setPositiveButton("OK") { dialog, _ ->
                val listView = (dialog as AlertDialog).listView
                val selectedLanguage = languages[listView.checkedItemPosition]
                onLanguageSelected(selectedLanguage)
            }
            .setNegativeButton("Cancel", null)
            .create()
    }
}
