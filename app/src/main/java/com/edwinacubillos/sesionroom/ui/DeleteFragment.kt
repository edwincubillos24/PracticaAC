package com.edwinacubillos.sesionroom.ui

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.edwinacubillos.sesionroom.R
import com.edwinacubillos.sesionroom.model.Deudor
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_delete.view.*

/**
 * A simple [Fragment] subclass.
 */
class DeleteFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var root = inflater.inflate(R.layout.fragment_delete, container, false)

        root.bt_eliminar.setOnClickListener {
            val nombre = root.et_deudor.text.toString()

            val database = FirebaseDatabase.getInstance()
            val myRef = database.getReference("deudores")

            var existeDeudor = false

            val postListener = object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (snapshot: DataSnapshot in dataSnapshot.children) {
                        System.out.println(dataSnapshot.toString())
                        val deudor = snapshot.getValue(Deudor::class.java)
                        if (deudor!!.name.equals(nombre)) {
                            val alertDialog: AlertDialog? = activity?.let {
                                val builder = AlertDialog.Builder(it)
                                builder.apply {
                                    setMessage("Desea Eliminar a " + deudor.name + " su deuda es: " + deudor.owe + "?")
                                    setPositiveButton(R.string.accept) { dialog, id ->
                                        myRef.child(deudor.id).removeValue()
                                    }
                                    setNegativeButton(R.string.cancel) { dialog, id ->
                                    }
                                }
                                builder.create()
                            }
                            alertDialog?.show()
                            break
                        }
                    }
                    if (!existeDeudor)
                        Toast.makeText(activity!!.applicationContext,"Deudor no encontrado",Toast.LENGTH_SHORT).show()
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Log.w("ListFragment:", "loadPost:onCancelled", databaseError.toException())
                }
            }
            myRef.addValueEventListener(postListener)
        }
        return root
    }


}

/*
            val deudorDao: DeudorDAO = SesionRoom.database.DeudorDAO()
            val deudor = deudorDao.searchDeudor(nombre)

            if (deudor != null) {
                val alertDialog: AlertDialog? = activity?.let {
                    val builder = AlertDialog.Builder(it)
                    builder.apply {
                        setMessage("Desea Eliminar a " + deudor.name + " su deuda es: " + deudor.owe + "?")
                        setPositiveButton(
                            R.string.accept
                        ) { dialog, id ->
                            deudorDao.deleteDeudor(deudor)
                        }
                        setNegativeButton(
                            R.string.cancel
                        ) { dialog, id ->
                        }
                    }
                    builder.create()
                }
                alertDialog?.show()
            } else {
                Toast.makeText(
                    activity!!.applicationContext,
                    "Deudor no encontrado",
                    Toast.LENGTH_SHORT
                ).show()
            }*/
