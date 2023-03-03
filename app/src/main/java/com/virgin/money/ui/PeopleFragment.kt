package com.virgin.money.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.textfield.TextInputLayout
import com.virgin.money.R
import com.virgin.money.data.database.DatabaseBuilder
import com.virgin.money.data.database.DatabaseHelperImpl
import com.virgin.money.data.database.entity.People
import com.virgin.money.data.network.NetworkHelperImpl
import com.virgin.money.data.network.NetworkUtils
import com.virgin.money.data.network.RetrofitBuilder
import com.virgin.money.data.viewmodel.PeopleViewModel
import com.virgin.money.data.viewmodel.SharedViewModel
import com.virgin.money.data.viewmodel.ViewModelFactory

import kotlinx.android.synthetic.main.fragment_people.*

/**
 * This page list people details
 */
class PeopleFragment : Fragment() {
    private lateinit var peopleViewModel: PeopleViewModel
    private lateinit var peopleAdapter: PeopleAdapter
    private lateinit var viewModel: SharedViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setupViewModel()
        setupPeopleObserver()
        return inflater.inflate(R.layout.fragment_people, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val searchView: TextInputLayout = view.findViewById(R.id.search)
        searchView.visibility= View.VISIBLE
        initUI()
        // this view-model is used to share data between two pages
        viewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]

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
        searchEdit.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
            }

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(text: CharSequence, start: Int,
                                       before: Int, count: Int) {
                    peopleAdapter.filter.filter((text.toString()))
            }
        })
        swipeRefreshLayout.setOnRefreshListener {
            peopleViewModel.refresh(NetworkUtils.isNetworkConnected(requireContext()))
        }
    }

    /**
     * View model invocation
     */
    private fun setupViewModel() {
        val isNetworkConnected = NetworkUtils.isNetworkConnected(requireContext())
        peopleViewModel = ViewModelProvider(
            this,
            ViewModelFactory(
                NetworkHelperImpl(RetrofitBuilder.networkService),
                DatabaseHelperImpl(DatabaseBuilder.getInstance(requireContext())),
                isNetworkConnected
            )
        )[PeopleViewModel::class.java]
        /**
         *  here I created a separate method for initialisation, because
         *  don't need to invoke the repository calls always
          */

    }
    /**
     * Handling network call and list the item based on result
     */
    private fun setupPeopleObserver() {
        peopleViewModel.getUiState().observe(requireParentFragment().viewLifecycleOwner) {
            when (it) {
                is UiState.Success -> {
                    progressBar.visibility = View.GONE
                    renderPeopleList(it.data)
                    if(!NetworkUtils.isNetworkConnected(requireContext()))
                        (activity as MainActivity?)!!.showSnackbar("Network connection lost!.", recyclerView)
                    recyclerView.visibility = View.VISIBLE
                    swipeRefreshLayout.isRefreshing = false
                }
                is UiState.Loading -> {
                    progressBar.visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE
                    swipeRefreshLayout.isRefreshing = false
                }
                is UiState.Error -> {
                    progressBar.visibility = View.GONE
                    swipeRefreshLayout.isRefreshing = false
                    (activity as MainActivity?)!!.showSnackbar(it.message, recyclerView)
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
        viewModel.setData(people)
        (activity as MainActivity?)!!.setupDetailsFragments()
    }
}