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
import kotlinx.android.synthetic.main.fragment_update.*
import kotlinx.android.synthetic.main.fragment_update.view.*

/**
 * A simple [Fragment] subclass.
 */
class UpdateFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_update, container, false)

        var bandera = 0
        var deudor = Deudor()
        root.bt_buscar.setOnClickListener {
            val nombre = root.et_deudor.text.toString()

            val database = FirebaseDatabase.getInstance()
            val myRef = database.getReference("deudores")

            if (bandera == 0) {
                val postListener = object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        for (snapshot: DataSnapshot in dataSnapshot.children) {
                            System.out.println(dataSnapshot.toString())
                            deudor = snapshot.getValue(Deudor::class.java)!!
                            if (deudor.name.equals(nombre)) {
                                et_nuevo_valor.setText(deudor.owe.toString())
                                et_nuevo_telefono.setText(deudor.phone)
                                bt_buscar.text = "ACTUALIZAR"
                                bandera = 1
                                break
                            }
                        }
                        if (bandera == 0)
                            Toast.makeText(activity!!.applicationContext,"Deudor no encontrado",Toast.LENGTH_SHORT).show()
                    }

                    override fun onCancelled(databaseError: DatabaseError) {
                        Log.w("ListFragment:", "loadPost:onCancelled", databaseError.toException())
                    }
                }
                myRef.addValueEventListener(postListener)
            } else {
                val childUpdates = HashMap<String, Any>()
                childUpdates["phone"] = et_nuevo_telefono.text.toString()
                childUpdates["name"] = nombre
                childUpdates["owe"] = et_nuevo_valor.text.toString().toInt()
                myRef.child(deudor.id).updateChildren(childUpdates)

                bt_buscar.text = "BUSCAR"
                bandera = 0
            }
        }
        return root
    }
}

/*    var idDeudor = 0
          var deudorDao: DeudorDAO = SesionRoom.database.DeudorDAO()


          if (bandera == 0) { // busqueda

              var deudor = deudorDao.searchDeudor(nombre)
              if (deudor != null) {
                  idDeudor = deudor.id
                  root.et_nuevo_telefono.setText(deudor.phone)
                  root.et_nuevo_valor.setText(deudor.owe.toString())
                  root.bt_buscar.text = "ACTUALIZAR"
                  root.et_deudor.isEnabled = false
                  bandera = 1
              } else {
                  Toast.makeText(
                      activity!!.applicationContext,
                      "Deudor no encontrado",
                      Toast.LENGTH_SHORT
                  ).show()
              }
          } else {  //bandera =1 va a actualizar
              val deudor = deudorDao.updateDeudor(
                  Deudor(
                      idDeudor,
                      nombre,
                      root.et_nuevo_telefono.text.toString(),
                      root.et_nuevo_valor.text.toString().toInt()
                  )
              )
              Toast.makeText(
                  activity!!.applicationContext,
                  "Deudor actualizado",
                  Toast.LENGTH_SHORT
              ).show()
              bandera = 0
              root.et_deudor.isEnabled = true
              root.bt_buscar.text = "BUSCAR"
          }*/
