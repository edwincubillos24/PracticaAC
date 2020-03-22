package com.edwinacubillos.sesionroom.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.edwinacubillos.sesionroom.MainActivity
import com.edwinacubillos.sesionroom.R
import com.edwinacubillos.sesionroom.RegistroActivity
import com.edwinacubillos.sesionroom.showToast
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), LoginMVP.View {

    private var loginPresenter: LoginMVP.Presenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginPresenter = LoginPresenter(this)

        bt_login.setOnClickListener {
            loginPresenter?.btLoginClicked()
        }

        tv_registro.setOnClickListener {
            loginPresenter?.btRegistroClicked()
        }
    }

    public override fun onStart() {
        super.onStart()
        loginPresenter?.loadUserConnected()
    }

    override fun goToMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    override fun goToRegistroActivity() {
        startActivity(Intent(this, RegistroActivity::class.java))
        finish()
    }

    override fun getCorreo(): String = et_correo.text.toString()

    override fun getPassword(): String = et_password.text.toString()

    override fun showInputErrorEmpty() {
        showToast(this, "Debe digitar todos los campos")
    }

    override fun showEmailFormatError() {
        showToast(this, "Correo no tiene un formato valido")
    }

    override fun showPasswordSizeError() {
        showToast(this, "Contraseña debe tener mínimo 5 caracteres")
    }

    override fun showErrorMsgUserNotExist() {
        showToast(this, "Usuario no existe")
    }

    override fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        progressBar.visibility = View.GONE
    }

    override fun showErrorMsgPassInvalido() {
        showToast(this, "Contraseña incorrecta")
    }
}
