package com.example.synergy7ch4.presentation.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.synergy7ch4.data.local.repository.PreferenceRepositoryImpl
import com.example.synergy7ch4.domain.PreferenceUseCase

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val preferenceUseCase = PreferenceUseCase(PreferenceRepositoryImpl(application))

    val getTheme = preferenceUseCase.executeGetTheme()
}