package com.virgin.money.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.textfield.TextInputLayout
import com.virgin.money.R
import com.virgin.money.data.database.DatabaseBuilder
import com.virgin.money.data.database.DatabaseHelperImpl
import com.virgin.money.data.database.entity.Room
import com.virgin.money.data.network.NetworkHelperImpl
import com.virgin.money.data.network.NetworkUtils
import com.virgin.money.data.network.RetrofitBuilder
import com.virgin.money.data.viewmodel.RoomViewModel
import com.virgin.money.data.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_people.*

/**
 * Used to list room details
 */
class RoomFragment : Fragment() {
    private lateinit var roomViewModel: RoomViewModel
    private lateinit var roomAdapter: RoomAdapter

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
        searchView.visibility= View.GONE
        initUI()
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
            roomViewModel.refresh(NetworkUtils.isNetworkConnected(requireContext()))
        }
    }
    private fun setupViewModel() {
        val isNetworkConnected = NetworkUtils.isNetworkConnected(requireContext())
        roomViewModel = ViewModelProvider(
            this,
            ViewModelFactory(
                NetworkHelperImpl(RetrofitBuilder.networkService),
                DatabaseHelperImpl(DatabaseBuilder.getInstance(requireContext())),
                isNetworkConnected
            )
        )[RoomViewModel::class.java]
    }
    /**
     * Handling network call and list the item based on result
     */
    private fun setupPeopleObserver() {
        roomViewModel.getUiState().observe(viewLifecycleOwner) {
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
                    (activity as MainActivity?)!!.showSnackbar(it.message, recyclerView)
                    swipeRefreshLayout.isRefreshing = false

                }
            }
        }
    }
    /**
     * Set result received from api to recyclerview
     */
    private fun renderPeopleList(peopleList: List<Room>){
        roomAdapter.addData(peopleList)
        roomAdapter.notifyDataSetChanged()
    }
}