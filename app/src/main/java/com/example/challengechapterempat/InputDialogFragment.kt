package com.example.challengechapterempat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.fragment_input_dialog.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
//tested, success
class InputDialogFragment : DialogFragment() {
    private var dbCatatan : CatatanDatabase? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_input_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dbCatatan = CatatanDatabase.getInstance(requireContext())
        tambah_button_input.setOnClickListener {
            GlobalScope.async {
                val judul = tambah_input_judul.text.toString()
                val catatan = tambah_input_catatan.text.toString()
                val process = dbCatatan?.catatanDao()?.insertCatatan(Catatan(null, judul, catatan))
                runOnUiThread {
                    if(process != 0.toLong()){
                        Toast.makeText(requireContext(), "Sukses", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(requireContext(), "Gagal", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            dismiss()
        }

    }
    private fun DialogFragment?.runOnUiThread(action: () -> Unit) {
        this ?: return
        if (!isAdded) return // Fragment not attached to an Activity
        activity?.runOnUiThread(action)
    }
}