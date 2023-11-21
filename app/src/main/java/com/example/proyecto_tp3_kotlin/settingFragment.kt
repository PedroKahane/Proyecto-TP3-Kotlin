package com.example.proyecto_tp3_kotlin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.activityViewModels
import com.example.proyecto_tp3_kotlin.viewmodels.SharedViewModel


class settingFragment : Fragment() {

    private val sharedViewModel: SharedViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setting, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toggleNightMode = view.findViewById(R.id.toggleNightMode) as SwitchCompat

        toggleNightMode.setOnCheckedChangeListener { _, isChecked ->
            sharedViewModel.setDarkMode(isChecked)
        }
    }
}