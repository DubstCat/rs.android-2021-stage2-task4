package com.example.storagetask.fragments

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import com.example.storagetask.R
import com.example.storagetask.data.Sort

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }

    override fun onPause() {
        super.onPause()
        loadSettings()
    }

    fun loadSettings(){
        val sp = PreferenceManager.getDefaultSharedPreferences(context)

        Sort.sorting_mode = sp.getString("filter", "")
    }
    
}