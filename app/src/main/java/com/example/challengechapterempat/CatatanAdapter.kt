package com.example.challengechapterempat

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.custom_layout_dialog_edit_data.view.*
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
            val customDialogDelete = LayoutInflater.from(it.context)
                .inflate(R.layout.custom_layout_dialog_hapus_data, null, false)
            val hapusDataDialog = AlertDialog.Builder(it.context)
                .setView(customDialogDelete)
                .create()
            customDialogDelete.hapus_dialog_button_cancel.setOnClickListener {
                hapusDataDialog.dismiss()
            }
            customDialogDelete.hapus_dialog_button_hapus.setOnClickListener {
                GlobalScope.async {
                    val result = dbCatatan?.catatanDao()?.deleteDataCatatan(listCatatan[position])

                    (customDialogDelete.context as MainActivity).runOnUiThread{
                        if(result != 0){
                            Toast.makeText(customDialogDelete.context, "Catatan ${listCatatan[position].judul} berhasil dihapus", Toast.LENGTH_SHORT).show()
                            (customDialogDelete.context as MainActivity).recreate()
                        }else{
                            Toast.makeText(customDialogDelete.context, "Catatan ${listCatatan[position].judul} gagal dihapus", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
            hapusDataDialog.show()
        }

        //edit data
        holder.itemView.button_edit.setOnClickListener {
            dbCatatan = CatatanDatabase.getInstance(it.context)
            val customDialogEdit = LayoutInflater.from(it.context)
                .inflate(R.layout.custom_layout_dialog_edit_data, null, false)
            val editDataDialog = AlertDialog.Builder(it.context)
                .setView(customDialogEdit)
                .create()
            customDialogEdit.edit_button_update_data.setOnClickListener {
                val newJudul = customDialogEdit.edit_input_judul.text.toString()
                val newCatatan = customDialogEdit.edit_input_catatan.text.toString()
                listCatatan[position].judul = newJudul
                listCatatan[position].catatan = newCatatan
                GlobalScope.async {
                    val command = dbCatatan?.catatanDao()?.updateDataCatatan(listCatatan[position])
                    (customDialogEdit.context as MainActivity).runOnUiThread{
                        if(command != 0){
                            Toast.makeText(it.context, "Catatan berhasil diupdate", Toast.LENGTH_SHORT).show()
                            (customDialogEdit.context as MainActivity).recreate()
                        }else{
                            Toast.makeText(it.context, "Catatan gagal diupdate", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
            editDataDialog.show()
        }


    }

    override fun getItemCount(): Int {
        return listCatatan.size
    }
}