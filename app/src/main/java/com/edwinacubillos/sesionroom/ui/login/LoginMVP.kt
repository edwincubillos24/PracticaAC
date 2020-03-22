package com.edwinacubillos.sesionroom.ui.login

import android.app.Activity
import com.google.firebase.auth.FirebaseUser

interface LoginMVP {

    interface View {
        fun getCorreo(): String
        fun getPassword(): String
        fun showInputErrorEmpty()
        fun showEmailFormatError()
        fun showPasswordSizeError()
        fun goToRegistroActivity()
        fun goToMainActivity()
        fun showErrorMsgUserNotExist()
        fun showProgressBar()
        fun hideProgressBar()
        fun showErrorMsgPassInvalido()
    }

    interface Presenter {
        fun btLoginClicked()
        fun btRegistroClicked()
        fun loadUserConnected()
        fun loginSuccessfull()
        fun showErrorMsgUserNotExist()
        fun userloaded(currentUser: FirebaseUser?)
        fun userNotLoaded()
        fun showErrorPassInvalido()
    }

    interface Model {
        fun logUser(
            correo: String,
            password: String)
        fun loadUserConnected()
    }
}