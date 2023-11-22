package com.example.proyecto_tp3_kotlin.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.activityViewModels
import com.example.proyecto_tp3_kotlin.R
import com.example.proyecto_tp3_kotlin.preferences.AppPreferences
import com.example.proyecto_tp3_kotlin.viewmodels.SharedViewModel


class SettingFragment : Fragment() {

    private val sharedViewModel: SharedViewModel by activityViewModels()
    private lateinit var appPreferences: AppPreferences
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_setting, container, false)
        val switchCompat = view.findViewById<SwitchCompat>(R.id.toggleNightMode)
        appPreferences = AppPreferences(requireContext())
        val isDarkModeEnabled = appPreferences.isDarkModeEnabled()

        switchCompat.isChecked = isDarkModeEnabled


        switchCompat.setOnCheckedChangeListener { _, isChecked ->
            // Cambia el estado del modo oscuro en AppPreferences cuando se cambia el SwitchCompat
            appPreferences.setDarkModeEnabled(isChecked)
        }
        return  view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toggleNightMode = view.findViewById(R.id.toggleNightMode) as SwitchCompat

        toggleNightMode.setOnCheckedChangeListener { _, isChecked ->
            sharedViewModel.setDarkMode(isChecked)
        }
    }
}