package com.example.teke.Fragment

import android.annotation.SuppressLint
import android.content.Context
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
import androidx.navigation.Navigation
import com.example.teke.R
import com.example.teke.User.RegisterDatabase
import com.example.teke.User.RegisterEntity
import com.example.teke.databinding.FragmentSignupBinding
import kotlinx.android.synthetic.main.fragment_signup.*

class SignupFragment : Fragment() {

    lateinit var binding: FragmentSignupBinding
    lateinit var database: RegisterDatabase
    val PREF_DATA = "user_room"
    lateinit var sp: SharedPreferences
    lateinit var editor: SharedPreferences.Editor

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSignupBinding.inflate(layoutInflater, container, false)
        database = RegisterDatabase.getInstance(requireContext())

        sp = this.requireActivity()
            .getSharedPreferences(PREF_DATA, Context.MODE_PRIVATE)

        binding.editNameSignup.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                Log.d("TAG", "beforeTextChanged: ")
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                Log.d("TAG", "onTextChanged: ")
            }

            override fun afterTextChanged(p0: Editable?) {
                if (p0.toString().isNotBlank()) {
                    binding.editNameSignup.error = null
                }
            }
        })

        binding.editEmailSignup.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                Log.d("TAG", "beforeTextChanged: ")
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                Log.d("TAG", "onTextChanged: ")
            }

            override fun afterTextChanged(p0: Editable?) {
                if (Patterns.EMAIL_ADDRESS.matcher(p0.toString()).matches()) {
                    binding.editEmailSignup.error = null
                }
            }
        })

        binding.editAddressSignup.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                Log.d("TAG", "beforeTextChanged: ")
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                Log.d("TAG", "onTextChanged: ")
            }

            override fun afterTextChanged(p0: Editable?) {
                if (p0.toString().isNotBlank()) {
                    binding.editAddressSignup.error = null
                }
            }
        })

        binding.editPasswordSignup.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                Log.d("TAG", "beforeTextChanged: ")
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                Log.d("TAG", "onTextChanged: ")
            }

            override fun afterTextChanged(p0: Editable?) {
                if (p0.toString().length >= 6) {
                    binding.editPasswordSignup.error = null
                }
            }
        })

        binding.editConfirmPasswordSignup.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                Log.d("TAG", "beforeTextChanged: ")
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                Log.d("TAG", "onTextChanged: ")
            }

            override fun afterTextChanged(p0: Editable?) {
                if (p0.toString().length >= 6) {
                    binding.editConfirmPasswordSignup.error = null
                }
            }
        })

        binding.btnSignup.setOnClickListener {
            if (checkInput()) {
                val name = editNameSignup.text.toString()
                val email = editEmailSignup.text.toString()
                val address = editAddressSignup.text.toString()
                val password = editPasswordSignup.text.toString()
                val cpassword = editConfirmPasswordSignup.text.toString()

                insertDb(name, email, address, password, cpassword)
            }
        }

        binding.textSignInClick.setOnClickListener {

            Navigation.findNavController(requireView())
                .navigate(R.id.action_signupFragment_to_signInFragment)
        }

        return binding.root
    }

    @SuppressLint("CommitPrefEdits")
    private fun insertDb(
        name: String,
        email: String,
        address: String,
        pass: String,
        cpass: String,
    ) {
        val user = RegisterEntity(0, name, email, address, pass, cpass)
        database.RegisterDatabaseDao().insert(
            RegisterEntity(
                user.userId,
                user.Name,
                user.email,
                user.address,
                user.passwrd,
                user.confirmPass
            )
        )
        Toast.makeText(requireContext(), "User Registered..!", Toast.LENGTH_SHORT).show()
        editor = sp.edit()
        editor.putString("name", name)
        editor.putString("email", email)
        editor.putString("address", address)
        editor.putBoolean("first", true)
        editor.commit()

        Navigation.findNavController(requireView())
            .navigate(R.id.action_signupFragment_to_signInFragment)
    }

    private fun signUp() {
        checkInput()
    }

    private fun checkInput(): Boolean {
        var valid: Boolean = false

        val passwrd: String = binding.editPasswordSignup.text.toString()

        if (editNameSignup.text!!.trim().toString().isEmpty()) {
            editNameSignup.error = "Please enter name"
        } else if (editEmailSignup.text!!.trim().toString().isEmpty()) {
            editEmailSignup.error = "Please enter email"
        } else if (!Patterns.EMAIL_ADDRESS.matcher(editEmailSignup.text.toString()).matches()) {
            editEmailSignup.error = "Please enter valid email"
        } else if (editAddressSignup.text!!.trim().toString().isEmpty()) {
            editAddressSignup.error = "Please enter address"
        } else if (editPasswordSignup.text!!.trim().toString().isEmpty()) {
            editPasswordSignup.error = "Please enter password"
        } else if (editPasswordSignup.text!!.trim().toString().length < 6) {
            editPasswordSignup.error = "Password must be more then 6 character"
        } else if (editNameSignup.text!!.trim().toString().isEmpty()) {
            editNameSignup.error = "Please enter name"
        } else if (editPasswordSignup.text.toString() != editConfirmPasswordSignup.text.toString()) {
            editConfirmPasswordSignup.error = "password and confirm password must be same.."
        } else {
            valid = true
        }
        return valid
    }
}