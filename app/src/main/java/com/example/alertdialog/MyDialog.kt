package com.example.alertdialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class MyDialog(val ctx: Context, private val onCitySelected: (String) -> Unit) : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var selectedCityIndex = 0
        val cities = arrayOf("New York", "Moscow", "Tokyo")

        return AlertDialog.Builder(ctx)
            .setTitle("Select City")
            .setSingleChoiceItems(cities, selectedCityIndex) { _, which -> selectedCityIndex = which }
            .setPositiveButton("OK") { _, _ ->
                onCitySelected(cities[selectedCityIndex])
            }
            .setNegativeButton("Cancel", null)
            .create()
    }
}
