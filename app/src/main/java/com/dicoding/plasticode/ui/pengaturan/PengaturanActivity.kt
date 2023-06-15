package com.dicoding.plasticode.ui.pengaturan

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.CompoundButton
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.dicoding.plasticode.databinding.ActivityPengaturanBinding
import com.dicoding.plasticode.service.UserPreference
import com.dicoding.plasticode.service.ViewModelFactory
import com.dicoding.plasticode.utils.dataStore


class PengaturanActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPengaturanBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPengaturanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        initSwitch()
        initListener()
    }

    private fun initSwitch() {
        val switchTheme = binding.switchModeLatar

        val pref = UserPreference.getInstance(dataStore)
        val settingViewModel = ViewModelProvider(
            this,
            ViewModelFactory(pref)
        )[PengaturanViewModel::class.java]

        settingViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                switchTheme.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                switchTheme.isChecked = false
            }
        }

        switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            settingViewModel.saveThemeSetting(isChecked)
        }
    }

    private fun initListener() {
        with(binding) {
            icBack.setOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
            onBackPressedDispatcher.addCallback(this@PengaturanActivity) {
                finish()
            }
        }
    }

    companion object {
        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, PengaturanActivity::class.java)
            context.startActivity(starter)
        }
    }
}