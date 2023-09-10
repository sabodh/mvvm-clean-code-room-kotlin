package com.contacts.people.presentation.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.contact.people.R
import com.contacts.people.data.local.entity.People
import com.contacts.people.data.network.NetworkResponse
import com.contacts.people.presentation.ui.activity.MainActivity
import com.contacts.people.presentation.ui.adapter.PeopleAdapter
import com.contacts.people.presentation.viewmodel.PeopleViewModel
import com.contacts.people.utils.CustomTextWatcher
import com.contacts.people.utils.NetworkUtils
import com.google.android.material.textfield.TextInputLayout

import kotlinx.android.synthetic.main.fragment_people.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

/**
 * This page list people details
 */
class PeopleListFragment : Fragment() {
    private val peopleViewModel: PeopleViewModel by inject()
    private val networkUtil : NetworkUtils by inject()
    private lateinit var peopleAdapter: PeopleAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_people, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val searchView: TextInputLayout = view.findViewById(R.id.search)
        searchView.visibility = View.VISIBLE
        initUI()
        initPeopleObserver()
        initViewModel()
    }

    /**
     * Initialising the UI views
     */
    private fun initUI() {
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        peopleAdapter =
            PeopleAdapter(
                arrayListOf()
            ){
                onItemClicked(it)
            }
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                (recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )
        recyclerView.adapter = peopleAdapter
        // search option

        val searchWatcher = object : CustomTextWatcher(){
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                peopleAdapter.filter.filter((s.toString()))
            }
        }
        searchEdit.addTextChangedListener(searchWatcher)
        swipeRefreshLayout.setOnRefreshListener {
            initViewModel()
        }
    }

    /**
     * View model invocation
     */
    private fun initViewModel() {
        peopleViewModel.isNetworkAvailable = networkUtil.isNetworkConnected()
        peopleViewModel.getContacts()
    }
    /**
     * Handling network call and list the item based on result
     */
    private fun initPeopleObserver() {

        lifecycleScope.launch {
            peopleViewModel.peopleLocalList.collect{

                when(it){
                    is NetworkResponse.Loading -> {
                        progressBar.visibility = View.VISIBLE
                        recyclerView.visibility = View.GONE
                        swipeRefreshLayout.isRefreshing = false
                    }
                    is NetworkResponse.Success -> {
                        progressBar.visibility = View.GONE
                        swipeRefreshLayout.isRefreshing = false
                        renderPeopleList(it.data)
                        if(!peopleViewModel.isNetworkAvailable)
                            (activity as MainActivity?)!!.showSnackbar("Network connection lost!.", recyclerView)
                        recyclerView.visibility = View.VISIBLE
                    }
                    is NetworkResponse.Error -> {
                        progressBar.visibility = View.GONE
                        swipeRefreshLayout.isRefreshing = false
                        (activity as MainActivity?)!!.showSnackbar(it.message, recyclerView)
                    }
                }
            }
        }
    }
    /**
     * Set result received from api to recyclerview
     */
    private fun renderPeopleList(peopleList: List<People>){
        peopleAdapter.addData(peopleList)
        peopleAdapter.notifyDataSetChanged()
    }

    /**
     * Selected person click event from adapter class
     */
    fun onItemClicked(people: People){
//        viewModel.setData(people)
//        (activity as MainActivity?)!!.setupDetailsFragments()
    }


}