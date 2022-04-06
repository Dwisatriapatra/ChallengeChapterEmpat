package com.example.challengechapterempat

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_login.*
//tested
class LoginFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        login_belum_punya_akun.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_registerFragment)
        }
        login_button_login.setOnClickListener {
            if(loginAuth()){
                Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_homeFragment)
            }
        }
    }

    private fun loginAuth() : Boolean {
        if(login_input_email.text.isNotEmpty() && login_input_password.text.isNotEmpty()){
            val sharedPreferences = requireContext().getSharedPreferences("DATAUSER", Context.MODE_PRIVATE)
            val dataEmail = sharedPreferences.getString("EMAIL", "")
            val dataPassword = sharedPreferences.getString("PASSWORD", "")

            val inputanEmail = login_input_email.text.toString()
            val inputanPassword = login_input_password.text.toString()
            return if(inputanEmail != dataEmail || inputanPassword != dataPassword){
                Toast.makeText(requireContext(), "email/password salah", Toast.LENGTH_SHORT).show()
                false
            }else{
                true
            }

        }else{
            Toast.makeText(requireContext(), "email dan password harus diisi", Toast.LENGTH_SHORT).show()
            return false
        }
    }
}