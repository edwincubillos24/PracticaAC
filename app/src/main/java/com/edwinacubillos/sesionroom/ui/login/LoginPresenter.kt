package com.edwinacubillos.sesionroom.ui.login

import com.edwinacubillos.sesionroom.isEmailValid
import com.google.firebase.auth.FirebaseUser

class LoginPresenter(
    val loginView: LoginMVP.View
) : LoginMVP.Presenter {

    private var loginModel: LoginMVP.Model = LoginModel(this)

    override fun btLoginClicked() {

        if (loginView.getCorreo().trim().equals("") || loginView.getPassword().trim().equals("")) {
            loginView.showInputErrorEmpty()
        } else {
            if (!isEmailValid(loginView.getCorreo().trim())) {
                loginView.showEmailFormatError()
            } else {
                if (loginView.getPassword().trim().length < 6) {
                    loginView.showPasswordSizeError()
                } else {
                    loginModel.logUser(
                        loginView.getCorreo().trim(),
                        loginView.getPassword().trim()
                    )
                    loginView.showProgressBar()
                }
            }
        }
    }

    override fun btRegistroClicked() {
        loginView.goToRegistroActivity()
    }

    override fun loadUserConnected() {
        loginView.showProgressBar()
        loginModel.loadUserConnected()
    }

    override fun loginSuccessfull() {
        loginView.hideProgressBar()
        loginView.goToMainActivity()
    }

    override fun showErrorMsgUserNotExist() {
        loginView.hideProgressBar()
        loginView.showErrorMsgUserNotExist()
    }

    override fun userloaded(currentUser: FirebaseUser?) {
        loginView.hideProgressBar()
        loginView.goToMainActivity()
    }

    override fun userNotLoaded() {
        loginView.hideProgressBar()
    }

    override fun showErrorPassInvalido() {
        loginView.hideProgressBar()
        loginView.showErrorMsgPassInvalido()
    }
}
