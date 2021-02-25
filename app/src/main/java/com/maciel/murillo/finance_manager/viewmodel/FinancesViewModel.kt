package com.maciel.murillo.finance_manager.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.maciel.murillo.finance_manager.model.AuthError
import com.maciel.murillo.finance_manager.model.service.AuthService
import com.maciel.murillo.finance_manager.model.service.DbService
import com.maciel.murillo.finance_manager.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class FinancesViewModel @Inject constructor(
    private val dbService: DbService,
    private val authService: AuthService
) : ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        coroutineContext.job.cancel()
    }
    private var job: Job? = null

    private val _logout = MutableLiveData<Event<Unit>>()
    val logout: LiveData<Event<Unit>> = _logout

    fun logout() {
        job = viewModelScope.launch(context = Dispatchers.IO + exceptionHandler) {
            authService.logout()
            _logout.postValue(Event(Unit))
        }
    }
}