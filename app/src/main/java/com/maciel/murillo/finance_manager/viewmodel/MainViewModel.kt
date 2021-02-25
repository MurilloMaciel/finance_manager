package com.maciel.murillo.finance_manager.viewmodel

import androidx.lifecycle.ViewModel
import com.maciel.murillo.finance_manager.model.service.AuthService
import com.maciel.murillo.finance_manager.model.service.DbService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val dbService: DbService
) : ViewModel() {



}