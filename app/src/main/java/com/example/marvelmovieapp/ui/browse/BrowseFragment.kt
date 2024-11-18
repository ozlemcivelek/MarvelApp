package com.example.marvelmovieapp.ui.browse

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.marvelmovieapp.adapter.TabPagerAdapter
import com.example.marvelmovieapp.databinding.FragmentBrowseBinding
import com.example.marvelmovieapp.models.HomeItem
import com.example.marvelmovieapp.models.ItemType
import com.example.marvelmovieapp.models.LoadingState
import com.example.marvelmovieapp.ui.home.HomeViewModel
import com.google.android.material.tabs.TabLayout

class BrowseFragment : Fragment() {

    private var _binding: FragmentBrowseBinding? = null
    private val binding get() = _binding!!

    private val tabEventsAdapter = TabPagerAdapter()
    private val tabCharactersAdapter = TabPagerAdapter()
    private val tabComicsAdapter = TabPagerAdapter()
    private val tabCreatorsAdapter = TabPagerAdapter()

    private lateinit var progressBar: ProgressBar

    private val viewModel by viewModels<HomeViewModel>()
    private val args by navArgs<BrowseFragmentArgs>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBrowseBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tabLayoutRecyclerView.adapter = tabEventsAdapter
        progressBar = binding.progressBar

        val tabLayout = binding.tabLayout

        args.itemType?.let {
            for (i in 0 until tabLayout.tabCount) {
                val tab = tabLayout.getTabAt(i)
                if (tab?.text == args.itemType) {
                    tab?.select()
                    break
                }
            }
        }

        observeAndHandleResponse(viewModel.events, tabEventsAdapter)
        observeAndHandleResponse(viewModel.characters, tabCharactersAdapter)
        observeAndHandleResponse(viewModel.comics, tabComicsAdapter)
        observeAndHandleResponse(viewModel.creators, tabCreatorsAdapter)

        observeLoadingResponse(viewModel.eventLoadingState)
        observeLoadingResponse(viewModel.characterLoadingState)
        observeLoadingResponse(viewModel.comicLoadingState)
        observeLoadingResponse(viewModel.creatorLoadingState)

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.text) {
                    ItemType.EVENTS.toString() -> binding.tabLayoutRecyclerView.adapter = tabEventsAdapter
                    ItemType.COMICS.toString() -> binding.tabLayoutRecyclerView.adapter = tabComicsAdapter
                    ItemType.CHARACTERS.toString() -> binding.tabLayoutRecyclerView.adapter = tabCharactersAdapter
                    ItemType.CREATORS.toString() -> binding.tabLayoutRecyclerView.adapter = tabCreatorsAdapter
                    //eklenecek, series ve stories
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })


    }

    private fun observeAndHandleResponse(
        liveData: MutableLiveData<List<HomeItem>>,
        adapter: TabPagerAdapter
    ) {
        liveData.observe(viewLifecycleOwner) { items ->
            if (items.isEmpty()) return@observe
            adapter.setItems(items)
        }

        adapter.onItemClicked = { item ->
          val action = BrowseFragmentDirections.actionBrowseFragmentToHomeDetail(item)
            findNavController().navigate(action)
        }
    }

    private fun observeLoadingResponse(
        liveData: MutableLiveData<LoadingState>,
    ) {
        liveData.observe(viewLifecycleOwner) { state ->
            when (state) {
                LoadingState.LOADING -> {
                    progressBar.visibility = View.VISIBLE
                }

                LoadingState.SUCCESS -> {
                    progressBar.visibility = View.GONE
                }

                else -> Unit
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}