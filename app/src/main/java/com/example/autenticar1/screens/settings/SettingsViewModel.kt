package com.example.autenticar1.screens.settings

import com.example.autenticar1.LOGIN_SCREEN
import com.example.autenticar1.SIGN_UP_SCREEN
import com.example.autenticar1.SPLASH_SCREEN
import com.example.autenticar1.model.service.AccountService
import com.example.autenticar1.model.service.LogService
import com.example.autenticar1.model.service.StorageService
import com.example.autenticar1.screens.AutenticarViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    logService: LogService,
    private val accountService: AccountService,
    private val storageService: StorageService
) : AutenticarViewModel(logService) {
    val uiState = accountService.currentUser.map {
        SettingsUiState(it.isAnonymous)
    }

    fun onLoginClick(openScreen: (String) -> Unit) = openScreen(LOGIN_SCREEN)

    fun onSignUpClick(openScreen: (String) -> Unit) = openScreen(SIGN_UP_SCREEN)

    fun onSignOutClick(restartApp: (String) -> Unit) {
        launchCatching {
            accountService.signOut()
            restartApp(SPLASH_SCREEN)
        }
    }

    fun onDeleteMyAccountClick(restartApp: (String) -> Unit) {
        launchCatching {
            accountService.deleteAccount()
            restartApp(SPLASH_SCREEN)
        }
    }

}