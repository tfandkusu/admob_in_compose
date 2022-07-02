package com.tfandkusu.template.view.info

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.tfandkusu.template.info.view.R
import com.tfandkusu.template.model.AppInfo
import com.tfandkusu.template.viewmodel.info.InfoEffect
import com.tfandkusu.template.viewmodel.info.InfoEvent
import com.tfandkusu.template.viewmodel.info.InfoViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class InfoFragment : PreferenceFragmentCompat() {
    companion object {
        private const val KEY_OSS = "oss"

        private const val KEY_ABOUT = "about"
    }

    private val viewModel: InfoViewModel by viewModels()

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preference_info, rootKey)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        findPreference<Preference>(KEY_OSS)?.setOnPreferenceClickListener {
            OssLicensesMenuActivity.setActivityTitle(getString(R.string.title_oss_license))
            val intent = Intent(requireContext(), OssLicensesMenuActivity::class.java)
            startActivity(intent)
            true
        }
        findPreference<Preference>(KEY_ABOUT)?.setOnPreferenceClickListener {
            viewModel.event(InfoEvent.OnClickAbout)
            true
        }
        lifecycleScope.launchWhenStarted {
            viewModel.effect.collect { effect ->
                when (effect) {
                    is InfoEffect.ShowAbout -> {
                        showAboutDialog(effect.numberOfStarts)
                    }
                }
            }
        }
    }

    private fun showAboutDialog(numberOfStarts: Int) {
        val adb = AlertDialog.Builder(requireContext())
        adb.setTitle(R.string.title_about)
        val sb = StringBuilder()
        sb.append(getString(R.string.app_name))
        sb.append('\n')
        sb.append(getString(R.string.version))
        sb.append(' ')
        sb.append(AppInfo.versionName)
        sb.append("\n\n")
        sb.append(getString(R.string.number_of_starts, numberOfStarts))
        sb.append("\n\n")
        sb.append(getString(R.string.copyright))
        sb.append(' ')
        sb.append(getString(R.string.author_name))
        adb.setMessage(sb.toString())
        adb.setPositiveButton(R.string.ok) { _, _ ->
        }
        adb.show()
    }
}
