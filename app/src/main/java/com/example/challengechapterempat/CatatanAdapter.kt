package com.example.challengechapterempat

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.custom_layout_dialog_hapus_data.view.*
import kotlinx.android.synthetic.main.item_adapter_catatan.view.*
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

@DelicateCoroutinesApi
class CatatanAdapter(private val listCatatan : List<Catatan>) : RecyclerView.Adapter<CatatanAdapter.ViewHolder>() {
    private var dbCatatan : CatatanDatabase? = null
    class ViewHolder(view : View) : RecyclerView.ViewHolder(view)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_adapter_catatan, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.tv_judul.text = "Judul : \n${listCatatan[position].judul}"
        holder.itemView.tv_catatan.text = "Catatan : \n${listCatatan[position].catatan}"
        //Delete data
        holder.itemView.button_delete.setOnClickListener {
            dbCatatan = CatatanDatabase.getInstance(it.context)
            val customDialog = LayoutInflater.from(it.context)
                .inflate(R.layout.custom_layout_dialog_hapus_data, null, false)
            val hapusDataDialog = AlertDialog.Builder(it.context)
                .setView(customDialog)
                .create()
            customDialog.hapus_dialog_button_cancel.setOnClickListener {
                hapusDataDialog.dismiss()
            }
            customDialog.hapus_dialog_button_hapus.setOnClickListener {
                GlobalScope.async {
                    val result = dbCatatan?.catatanDao()?.deleteDataCatatan(listCatatan[position])

                    (customDialog.context as MainActivity).runOnUiThread{
                        if(result != 0){
                            Toast.makeText(customDialog.context, "Catatan ${listCatatan[position].judul} berhasil dihapus", Toast.LENGTH_SHORT).show()
                            (customDialog.context as MainActivity).recreate()
                        }else{
                            Toast.makeText(customDialog.context, "Catatan ${listCatatan[position].judul} gagal dihapus", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
            hapusDataDialog.show()
        }
    }

    override fun getItemCount(): Int {
        return listCatatan.size
    }
}