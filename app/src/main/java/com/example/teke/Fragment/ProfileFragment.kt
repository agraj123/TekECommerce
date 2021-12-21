package com.example.teke.Fragment

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.teke.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    lateinit var binding: FragmentProfileBinding
    lateinit var sp: SharedPreferences
    lateinit var editor: SharedPreferences.Editor
    val PREF_DATA = "user_room"

    @SuppressLint("CommitPrefEdits")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentProfileBinding.inflate(layoutInflater, container, false)

        sp = this.requireActivity()
            .getSharedPreferences(PREF_DATA, Context.MODE_PRIVATE)

        val name: String = sp.getString("name", "")!!
        val email: String = sp.getString("email", "")!!
        val address: String = sp.getString("address", "")!!

        binding.profileName.text = name
        binding.profileEmail.text = email

        binding.profileNameText.text = name
        binding.profileEmailText.text = email

        binding.profileAddressText.text = address

        return binding.root
    }
}