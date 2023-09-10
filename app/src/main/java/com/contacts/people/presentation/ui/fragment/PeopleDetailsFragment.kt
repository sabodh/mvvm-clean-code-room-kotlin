package com.contacts.people.presentation.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.contact.people.R
import com.contact.people.databinding.FragmentProfileBinding

import com.contacts.people.presentation.viewmodel.SharedViewModel
import kotlinx.android.synthetic.main.fragment_profile.*

class PeopleDetailsFragment : Fragment() {
    private lateinit var peopleViewModel: SharedViewModel
    private lateinit var binding: FragmentProfileBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_profile,
            container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        peopleViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        peopleViewModel.peopleDetails.observe(viewLifecycleOwner, Observer {
            // data-binding to view
            binding.profile = it
            // handled email feature
            val emailAddress = it.email.toString()
            email.setOnClickListener {
                emailAddress.let { d ->
                    if (!TextUtils.isEmpty(d)) {
                        val intent = Intent(Intent.ACTION_SEND).apply {
                            type = "message/rfc822"
                            putExtra(Intent.EXTRA_EMAIL, arrayOf(emailAddress))
                        }
                        startActivity(Intent.createChooser(intent, "Send email using:"))
                    }
                }


            }
        })
    }
}