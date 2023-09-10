package com.contacts.people.presentation.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.contact.people.R
import com.google.android.material.textfield.TextInputLayout
import com.contacts.people.data.local.entity.Room
import com.contacts.people.data.network.NetworkResponse
import com.contacts.people.presentation.ui.activity.MainActivity
import com.contacts.people.presentation.ui.adapter.RoomAdapter
import com.contacts.people.presentation.viewmodel.RoomViewModel
import com.contacts.people.utils.NetworkUtils
import kotlinx.android.synthetic.main.fragment_people.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

/**
 * Used to list room details
 */
class RoomListFragment : Fragment() {
    private val roomViewModel: RoomViewModel by inject()
    private val networkUtils: NetworkUtils by inject()
    private lateinit var roomAdapter: RoomAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_people, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val searchView: TextInputLayout = view.findViewById(R.id.search)
        searchView.visibility = View.GONE
        initUI()
        initPeopleObserver()
        initViewModel()
    }

    private fun initUI() {
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        roomAdapter =
            RoomAdapter(
                arrayListOf()
            )
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                (recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )
        recyclerView.adapter = roomAdapter
        swipeRefreshLayout.setOnRefreshListener {
            initViewModel()
        }
    }

    /**
     * Get the Room details from server
     */
    private fun initViewModel() {
        roomViewModel.isNetworkAvailable = networkUtils.isNetworkConnected()
        roomViewModel.getRooms()
    }

    /**
     * Handling network call and list the item based on result
     */
    private fun initPeopleObserver() {

        lifecycleScope.launch {
            roomViewModel.roomLocalList.collect {
                when (it) {
                    is NetworkResponse.Loading -> {
                        progressBar.visibility = View.VISIBLE
                        recyclerView.visibility = View.GONE
                        swipeRefreshLayout.isRefreshing = false
                    }
                    is NetworkResponse.Success -> {
                        progressBar.visibility = View.GONE
                        swipeRefreshLayout.isRefreshing = false
                        renderPeopleList(it.data)
                        if (!roomViewModel.isNetworkAvailable)
                            (activity as MainActivity?)!!.showSnackbar(
                                "Network connection lost!.",
                                recyclerView
                            )
                        recyclerView.visibility = View.VISIBLE
                    }
                    is NetworkResponse.Error -> {
                        progressBar.visibility = View.GONE
                        (activity as MainActivity?)!!.showSnackbar(it.message, recyclerView)
                        swipeRefreshLayout.isRefreshing = false

                    }
                }
            }
        }


    }

    /**
     * Set result received from api to recyclerview
     */
    private fun renderPeopleList(peopleList: List<Room>) {
        roomAdapter.addData(peopleList)
        roomAdapter.notifyDataSetChanged()
    }
}