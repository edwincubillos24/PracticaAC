package com.edwinacubillos.sesionroom.ui.login

import android.app.Activity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginModel(val loginPresenter: LoginMVP.Presenter) : LoginMVP.Model {

    val auth = FirebaseAuth.getInstance()

    override fun logUser(
        correo: String,
        password: String,
        activity: Activity
    ) {
        auth.signInWithEmailAndPassword(correo, password)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    loginPresenter.loginSuccessfull()
                } else {
                    if (task.exception!!.message.equals("There is no user record corresponding to this identifier. The user may have been deleted.")) {
                        loginPresenter.showErrorMsgUserNotExist()
                    } else {
                        if (task.exception!!.message.equals("The password is invalid or the user does not have a password.")) {
                            loginPresenter.showErrorPassInvalido()
                        }
                    }
                }
            }
    }

    override fun loadUserConnected() {
        val currentUser: FirebaseUser? = auth.currentUser
        if (currentUser != null)
            loginPresenter.userloaded(currentUser)
        else
            loginPresenter.userNotLoaded()
    }
}
