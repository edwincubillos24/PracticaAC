package com.edwinacubillos.sesionroom

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.edwinacubillos.sesionroom.model.Usuario
import com.edwinacubillos.sesionroom.ui.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_registro.*
import java.util.regex.Matcher
import java.util.regex.Pattern

class RegistroActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        bt_login.setOnClickListener {

            if (isEmailValid(et_correo.text.toString())) {
                createUserInFirebaseAuth()
            } else {
                Toast.makeText(
                    this,
                    "El correo dado no es un correo electronico",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun createUserInFirebaseAuth() {
        val auth = FirebaseAuth.getInstance()
        auth.createUserWithEmailAndPassword(
                et_correo.text.toString(),
                et_password.text.toString()
            )
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    createUserDatabase(user)
                    goToLoginActivity()
                } else {
                    Log.w("LoginActivity", "signInWithEmail:failure", task.exception)
                    if (task.exception!!.message.equals("The email address is already in use by another account.")) {
                        Toast.makeText(
                                this,
                                "Ya existe un usuario con esa cuenta",
                                Toast.LENGTH_SHORT
                            )
                            .show()
                    }
                }
            }
    }

    private fun goToLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun createUserDatabase(user: FirebaseUser?) {
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("usuarios")
        val usuario = Usuario(user!!.uid, user.email.toString())
        myRef.child(user.uid).setValue(usuario)
    }

    fun isEmailValid(email: String?): Boolean {
        val expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$"
        val pattern: Pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
        val matcher: Matcher = pattern.matcher(email)
        return matcher.matches()
    }
}
