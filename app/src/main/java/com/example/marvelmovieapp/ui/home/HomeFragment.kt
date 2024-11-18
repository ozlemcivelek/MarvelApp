package com.example.marvelmovieapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.marvelmovieapp.adapter.HomeItemAdapter
import com.example.marvelmovieapp.adapter.ImageViewPagerAdapter
import com.example.marvelmovieapp.databinding.FragmentHomeBinding
import com.example.marvelmovieapp.models.HomeItem
import com.example.marvelmovieapp.models.ItemType
import com.example.marvelmovieapp.models.LoadingState

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<HomeViewModel>()

    private val imageViewPagerAdapter = ImageViewPagerAdapter()

    private val eventsRecyclerViewAdapter = HomeItemAdapter()
    private val charactersRecyclerViewAdapter = HomeItemAdapter()
    private val creatorsRecyclerViewAdapter = HomeItemAdapter()
    private val comicsRecyclerViewAdapter = HomeItemAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeLoadingResponse(viewModel.sliderLoadingState, binding.progressBarSlider)
        observeLoadingResponse(viewModel.eventLoadingState, binding.progressBarEvent)
        observeLoadingResponse(viewModel.characterLoadingState, binding.progressBarCharacters)
        observeLoadingResponse(viewModel.creatorLoadingState, binding.progressBarCreators)
        observeLoadingResponse(viewModel.comicLoadingState, binding.progressBarComics)

        setupRecyclerViewAdapters()
        observeAndHandleSeriesResponse()

        observeAndHandleResponse(viewModel.events, eventsRecyclerViewAdapter)
        observeAndHandleResponse(viewModel.characters, charactersRecyclerViewAdapter)
        observeAndHandleResponse(viewModel.creators, creatorsRecyclerViewAdapter)
        observeAndHandleResponse(viewModel.comics, comicsRecyclerViewAdapter)
    }

    private fun observeAndHandleSeriesResponse() {
        imageViewPagerAdapter.onItemClicked = { sliderModel ->
            actionDetailFragment(
                HomeItem(
                    id = sliderModel.id,
                    type = ItemType.SERIES.toString(),
                    imageUrl = sliderModel.imageUrl,
                    imageTitle = sliderModel.imageTitle,
                    description = ""
                )
            )
        }
        viewModel.imageSlider.observe(viewLifecycleOwner) { series ->
            if (series.isEmpty()) return@observe
            //initializing the adapter
            imageViewPagerAdapter.setItems(series)
            setUpViewPager()
        }

    }

    private fun observeAndHandleResponse(
        liveData: MutableLiveData<List<HomeItem>>,
        adapter: HomeItemAdapter
    ) {
        liveData.observe(viewLifecycleOwner) { items ->
            if (items.isEmpty()) return@observe

            val limitedItems = if (items.size > 3) items.take(3) else items
            adapter.setItems(limitedItems)
        }

        adapter.onItemClicked = { item ->
            actionDetailFragment(item)
        }

        adapter.onBrowseAllClicked = { itemType->
            val action = HomeFragmentDirections.actionHomeFragmentToBrowseFragment(itemType) // ItemType gelecek.
            findNavController().navigate(action)
        }
    }

    private fun actionDetailFragment(item: HomeItem) {
        val action = HomeFragmentDirections.actionHomeFragmentToHomeDetail(item)
        findNavController().navigate(action)
    }

    private fun observeLoadingResponse(
        liveData: MutableLiveData<LoadingState>,
        progressBar: ProgressBar) {
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

    private fun setupRecyclerViewAdapters() {
        binding.eventsRecyclerView.adapter = eventsRecyclerViewAdapter
        binding.charactersRecyclerView.adapter = charactersRecyclerViewAdapter
        binding.creatorsRecyclerView.adapter = creatorsRecyclerViewAdapter
        binding.comicsRecyclerView.adapter = comicsRecyclerViewAdapter
    }

    private fun setUpViewPager() {

        binding.viewPager.adapter = imageViewPagerAdapter

        //set the orientation of the viewpager using ViewPager2.orientation
        binding.viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        //select any page you want as your starting page
        val currentPageIndex = 1
        binding.viewPager.currentItem = currentPageIndex

        val currentPage = 1
        val totalPages = viewModel.imageSlider.value?.size ?: 0
        val initialPageInfo = "$currentPage/$totalPages"
        binding.imageNumber.text = initialPageInfo

        // registering for page change callback
        binding.viewPager.registerOnPageChangeCallback(
            object : ViewPager2.OnPageChangeCallback() {

                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)

                    val currentPageInfo = "${(position)}/$totalPages"
                    binding.imageNumber.text = currentPageInfo
                }
            }
        )
    }

    override fun onDestroy() {
        super.onDestroy()

        // unregistering the onPageChangedCallback
        binding.viewPager.unregisterOnPageChangeCallback(
            object : ViewPager2.OnPageChangeCallback() {}
        )
    }
}
