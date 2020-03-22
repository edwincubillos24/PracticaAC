package com.edwinacubillos.sesionroom.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.edwinacubillos.sesionroom.R
import com.edwinacubillos.sesionroom.SesionRoom
import com.edwinacubillos.sesionroom.model.Deudor
import com.edwinacubillos.sesionroom.model.DeudorDAO
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ListFragment : Fragment() {

    var allDeudores: MutableList<Deudor> = mutableListOf()
    lateinit var deudoresRVAdapter : DeudoresRVAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_list, container, false)

        val rv_deudores = root.findViewById<RecyclerView>(R.id.rv_deudores)

        rv_deudores.layoutManager = LinearLayoutManager(
            activity!!.applicationContext,
            RecyclerView.VERTICAL,
            false
        )
        rv_deudores.setHasFixedSize(true)

   //     var deudorDao: DeudorDAO = SesionRoom.database.DeudorDAO()
   //     allDeudores = deudorDao.getDeudores()

       deudoresRVAdapter = DeudoresRVAdapter(
            activity!!.applicationContext,
            allDeudores as ArrayList<Deudor>
        )

        rv_deudores.adapter = deudoresRVAdapter

        return root
    }

    override fun onResume() {
        super.onResume()
        cargarDeudores()
    }

    private fun cargarDeudores() {
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("deudores")

        allDeudores.clear()

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for(snapshot in dataSnapshot.children) {
                    System.out.println(dataSnapshot.toString())
                    val deudor = snapshot.getValue(Deudor::class.java)
                    allDeudores.add(deudor!!)
                }
                deudoresRVAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w("ListFragment:", "loadPost:onCancelled", databaseError.toException())
            }
        }
        myRef.addValueEventListener(postListener)
    }
}