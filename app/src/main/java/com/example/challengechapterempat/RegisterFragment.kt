package com.example.challengechapterempat

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_register.*
//tested
class RegisterFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //action for button register
        register_button_daftar.setOnClickListener {
            //checking all field, all field required
            if(register_input_username.text.isNotEmpty() &&
                register_input_email.text.isNotEmpty() &&
                register_input_password.text.isNotEmpty() &&
                register_input_konfirmasi_password.text.isNotEmpty()){
                //check the similarity between the password field and confirm password
                if(register_input_password.text.toString() != register_input_konfirmasi_password.text.toString()){
                    Toast.makeText(requireContext(), "Password dan konfirmasi password harus sama", Toast.LENGTH_SHORT).show()
                }else{
                    //if similar, then input user data to shared preference
                    inputUserData()
                    Navigation.findNavController(view).navigate(R.id.action_registerFragment_to_loginFragment)
                }
            }else{
                Toast.makeText(requireContext(), "Semua data belum terisi", Toast.LENGTH_SHORT).show()
            }
        }
    }

    //function for input data in shared preference
    private fun inputUserData(){
        val dataUsername = register_input_username.text.toString()
        val dataEmail = register_input_email.text.toString()
        val datapassword = register_input_password.text.toString()
        val dataKonfirmasiPassword = register_input_konfirmasi_password.text.toString()

        val sharedPreference = requireContext().getSharedPreferences("DATAUSER", Context.MODE_PRIVATE)
        val spf = sharedPreference.edit()
        spf.putString("USERNAME", dataUsername)
        spf.putString("EMAIL", dataEmail)
        spf.putString("PASSWORD", datapassword)
        spf.putString("KONFIRMASIPASSWORD", dataKonfirmasiPassword)
        spf.apply()
    }
}