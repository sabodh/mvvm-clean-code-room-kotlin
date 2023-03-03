package com.virgin.money.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.virgin.money.R

/**
 * Implementation of Pager
 */
class CollectionFragment : Fragment() {
    private lateinit var demoCollectionAdapter: CollectionAdapter
    private lateinit var viewPager: ViewPager2
    private val ROOM = "ROOM"
    private val CONTACT = "CONTACT"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_pager, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        demoCollectionAdapter = CollectionAdapter(this)
        viewPager = view.findViewById(R.id.pager)
        viewPager.adapter = demoCollectionAdapter
        val nameList = arrayListOf(CONTACT, ROOM)
        val tabLayout = view.findViewById<TabLayout>(R.id.tab_layout)
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = (nameList[position])
        }.attach()
    }
}

class CollectionAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = PAGE_COUNT
    override fun createFragment(position: Int): Fragment {
        val fragment = if (position == 0) {
            PeopleFragment()
        } else {
            RoomFragment()
        }
        return fragment
    }
}

private const val PAGE_COUNT = 2

