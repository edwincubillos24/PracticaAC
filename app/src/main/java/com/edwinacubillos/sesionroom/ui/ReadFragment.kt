package com.edwinacubillos.sesionroom.ui

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
import kotlinx.android.synthetic.main.fragment_read.*
import kotlinx.android.synthetic.main.fragment_read.view.*
import kotlinx.android.synthetic.main.fragment_update.*

class ReadFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_read, container, false)

        root.bt_search.setOnClickListener {
            val nombre = root.et_nombre.text.toString()

            val database = FirebaseDatabase.getInstance()
            val myRef = database.getReference("deudores")

            var existeDeudor = false

            val postListener = object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (snapshot: DataSnapshot in dataSnapshot.children) {
                        System.out.println(dataSnapshot.toString())
                        val deudor = snapshot.getValue(Deudor::class.java)
                        if (deudor!!.name.equals(nombre)) {
                            tv_deuda.text = deudor.owe.toString()
                            tv_nombre.text = deudor.name
                            tv_telefono.text = deudor.phone
                            existeDeudor = true
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


            /*       val deudorDao: DeudorDAO = SesionRoom.database.DeudorDAO()
                   val deudor = deudorDao.searchDeudor(nombre)

                   if(deudor != null) {
                       tv_nombre.text = deudor.name
                       tv_deuda.text = deudor.owe.toString()
                       tv_telefono.text = deudor.phone
                   } else{
                       Toast.makeText(
                           activity!!.applicationContext,
                           "Deudor no encontrado",
                           Toast.LENGTH_SHORT
                       ).show()
                   }*/

            return root
        }
    }