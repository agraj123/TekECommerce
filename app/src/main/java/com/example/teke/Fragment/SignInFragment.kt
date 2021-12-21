package com.example.teke.Fragment

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import com.example.teke.Dashboard
import com.example.teke.R
import com.example.teke.User.RegisterDatabase
import com.example.teke.User.RegisterEntity
import com.example.teke.databinding.FragmentSignInBinding
import kotlinx.android.synthetic.main.fragment_sign_in.*
import com.google.android.material.snackbar.Snackbar


class SignInFragment : Fragment() {

    lateinit var binding: FragmentSignInBinding
    lateinit var database: RegisterDatabase

    val PREF_DATA = "user_room"
    lateinit var sp: SharedPreferences
    lateinit var editor: SharedPreferences.Editor


    var sharedpreferences: SharedPreferences? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentSignInBinding.inflate(layoutInflater, container, false)
        database = RegisterDatabase.getInstance(requireContext())

        sp = this.requireActivity()
            .getSharedPreferences(PREF_DATA, Context.MODE_PRIVATE)

        val first = sp.getBoolean("first", true)

        binding.editEmailSignin.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                Log.d("TAG", "beforeTextChanged: ")
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                Log.d("TAG", "onTextChanged: ")
            }

            override fun afterTextChanged(p0: Editable?) {
                if (Patterns.EMAIL_ADDRESS.matcher(p0.toString()).matches()) {
                    editEmailSignin.error = null
                }
            }
        })
        binding.editPasswordSignin.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                Log.d("TAG", "beforeTextChanged: ")
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                Log.d("TAG", "onTextChanged: ")
            }

            override fun afterTextChanged(p0: Editable?) {
                if (p0.toString().length >= 6) {
                    editPasswordSignin.error = null
                }
            }
        })

        binding.btnSignin.setOnClickListener {
            if (checkInput()) {
                val email = editEmailSignin.text.toString()
                val pass = editPasswordSignin.text.toString()
                checkFromDb(email, pass, first)

            }
        }

        binding.textSignInClick.setOnClickListener {
            Navigation.findNavController(requireView())
                .navigate(R.id.action_signInFragment_to_signupFragment)
        }
        return binding.root
    }

    @SuppressLint("CommitPrefEdits")
    private fun checkFromDb(
        email: String,
        pass: String,
        first: Boolean,
    ) {
        val user = RegisterEntity(0, "", email, "", pass, "")
        val login = database.RegisterDatabaseDao().getUser(email, pass)
        if (login != null) {
            editor = sp.edit()
            editor.putString("email", login.email)
            editor.putString("password", login.passwrd)
            if (first) {
                editor.putBoolean("first", true)
            } else {
                editor.putBoolean("first", false)
            }
            editor.commit()

            val intent = Intent(requireContext(), Dashboard::class.java)
            startActivity(intent)

            Snackbar.make(requireView(), "Welcome $user", Snackbar.LENGTH_LONG).show()

            //Toast.makeText(requireContext(), "Logged In!", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(
                requireContext(),
                "User not found..! \n Please try again.",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun checkInput(): Boolean {
        var valid: Boolean = false

        if (editEmailSignin.text!!.trim().toString().isEmpty()) {
            editEmailSignin.error = "Please enter email"
        } else if (!Patterns.EMAIL_ADDRESS.matcher(editEmailSignin.text.toString()).matches()) {
            editEmailSignin.error = "Please enter valid email"
        } else if (editPasswordSignin.text!!.trim().toString().isEmpty()) {
            editPasswordSignin.error = "Please enter password"
        } else if (editPasswordSignin.text!!.trim().toString().length < 6) {
            editPasswordSignin.error = "Password must be more then 6 character"
        } else {
            valid = true
        }
        return valid
    }
}