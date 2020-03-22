package com.edwinacubillos.sesionroom.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.edwinacubillos.sesionroom.R
import com.edwinacubillos.sesionroom.SesionRoom
import com.edwinacubillos.sesionroom.model.Deudor
import com.edwinacubillos.sesionroom.model.DeudorDAO
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_create.view.*
import java.sql.Types.NULL

class CreateFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_create, container, false)

        root.bt_save.setOnClickListener {
            val nombre = root.et_name.text.toString()
            val telefono = root.et_phone.text.toString()
            val cantidad = root.et_owe.text.toString()

       //     guardarEnRoom(nombre, telefono, cantidad)

            guardarEnFirebase(nombre, telefono, cantidad)

        }
        return root
    }


    fun guardarEnFirebase(nombre: String, telefono: String, cantidad: String) {

        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("deudores")
        var idDeudor = myRef.push().key

        val deudor = Deudor(idDeudor!!, nombre, telefono, cantidad.toInt())

        myRef.child(idDeudor).setValue(deudor)
    }

  /*  private fun guardarEnRoom(nombre: String, telefono: String, cantidad: String) {
        val deudor = Deudor(NULL, nombre, telefono, cantidad.toInt())

        var deudorDao: DeudorDAO = SesionRoom.database.DeudorDAO()

        deudorDao.insertDeudor(deudor)
    }*/
}