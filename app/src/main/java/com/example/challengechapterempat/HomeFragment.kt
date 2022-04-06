package com.example.challengechapterempat

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        val sharedPreferences = requireContext().getSharedPreferences("DATAUSER", Context.MODE_PRIVATE)
        val username = sharedPreferences.getString("USERNAME", "")
        home_username_text.text = "Halo, $username"

        fab_tambah.setOnClickListener {
            InputDialogFragment().show(childFragmentManager, "InputDialogFragment")
        }
        dbCatatan = CatatanDatabase.getInstance(requireContext())
        getDataCatatan()
    }
    private fun getDataCatatan() {
        rv_catatan.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        val listCatatan = dbCatatan?.catatanDao()?.getAllCatatan()
        GlobalScope.launch {
            activity?.runOnUiThread{
                listCatatan.let {
                    rv_catatan.adapter = CatatanAdapter(it!!)
                }
            }
        }
    }
}