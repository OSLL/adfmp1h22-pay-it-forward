package com.example.payitforward.ui.settings

import android.content.Intent
import android.os.Bundle
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.example.payitforward.*


class SettingsFragment : PreferenceFragmentCompat() {

    private inline fun onPreferenceChange(key: String, crossinline func: (value: Any) -> Unit) {
        preferenceManager.findPreference<Preference>(key)?.onPreferenceChangeListener =
            Preference.OnPreferenceChangeListener { _, value ->
                func(value)
                true
            }
    }

    private inline fun onPreferenceClick(key: String, crossinline func: () -> Unit) {
        preferenceManager.findPreference<Preference>(key)?.onPreferenceClickListener =
            Preference.OnPreferenceClickListener {
                func()
                true
            }
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)

        activity?.apply {

//            onPreferenceClick("Edit Profile") {
//                val intent = Intent(this, EditProfileActivity::class.java)
//                startActivity(intent)
//            }
//            onPreferenceClick("Security") {
//                val intent = Intent(this, ChangePasswordActivity::class.java)
//                startActivity(intent)
//            }
//            onPreferenceClick("About") {
//                val intent = Intent(this, AboutActivity::class.java)
//                startActivity(intent)
//              }
        }

    }
}

