package com.example.challengechapterempat

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@DelicateCoroutinesApi
class HomeFragment : Fragment() {
    private var dbCatatan : CatatanDatabase? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }
    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //set text of username in home
        val sharedPreferences = requireContext().getSharedPreferences("DATAUSER", Context.MODE_PRIVATE)
        val username = sharedPreferences.getString("USERNAME", "")
        home_username_text.text = "Halo, $username"

        //action button for tambah data
        fab_tambah.setOnClickListener {
            InputDialogFragment().show(childFragmentManager, "InputDialogFragment")
        }

        //setting view of recycler view in home fragment
        dbCatatan = CatatanDatabase.getInstance(requireContext())
        getDataCatatan()

        //action for logout
        logout.setOnClickListener {
            logout()
        }
    }

    //function to get data of catatanDatabase
    private fun getDataCatatan() {
        //define layout manager
        rv_catatan.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        //command for room database that return all of data
        val listCatatan = dbCatatan?.catatanDao()?.getAllCatatan()

        GlobalScope.launch {
            activity?.runOnUiThread{
                listCatatan.let {
                    rv_catatan.adapter = CatatanAdapter(it!!)
                }
            }
        }
    }

    //function for logout action
    private fun logout(){
        AlertDialog.Builder(requireContext())
            .setTitle("LOGOUT")
            .setMessage("Yakin ingin logout?")
            .setNegativeButton("Tidak"){ dialogInterface: DialogInterface, i: Int ->
                dialogInterface.dismiss()
            }.setPositiveButton("Ya"){ dialogInterface: DialogInterface, i: Int ->
                val sharedPreferences = requireContext().getSharedPreferences("DATAUSER", Context.MODE_PRIVATE)
                val sf = sharedPreferences.edit()
                sf.clear()
                sf.apply()

                //reload activity
                val mIntent = activity?.intent
                activity?.finish()
                startActivity(mIntent)
            }.show()
    }
}