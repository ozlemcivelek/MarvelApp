package com.example.marvelmovieapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.marvelmovieapp.adapter.HomeItemAdapter
import com.example.marvelmovieapp.adapter.ImageViewPagerAdapter
import com.example.marvelmovieapp.databinding.FragmentHomeBinding
import com.example.marvelmovieapp.models.HomeItem

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<HomeViewModel>()

    private lateinit var imageViewPagerAdapter: ImageViewPagerAdapter

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

        setupRecyclerViewAdapters()

        observeAndHandleSeriesResponse()

        observeAndHandleResponse(viewModel.events, eventsRecyclerViewAdapter)
        observeAndHandleResponse(viewModel.characters, charactersRecyclerViewAdapter)
        observeAndHandleResponse(viewModel.creators, creatorsRecyclerViewAdapter)
        observeAndHandleResponse(viewModel.comics, comicsRecyclerViewAdapter)
    }

    private fun observeAndHandleSeriesResponse() {
        viewModel.imageSlider.observe(viewLifecycleOwner) { series ->
            if (series.isEmpty()) return@observe
            //initializing the adapter
            imageViewPagerAdapter = ImageViewPagerAdapter(series)
            setUpViewPager()
        }
    }

    private fun observeAndHandleResponse(
        liveData: MutableLiveData<List<HomeItem>>,
        adapter: HomeItemAdapter
    ) {
        liveData.observe(viewLifecycleOwner) { items ->
            if (items.isEmpty()) return@observe
            adapter.setItems(items)
        }

        adapter.onItemClicked = { item ->
            val action = HomeFragmentDirections.actionHomeFragmentToHomeDetail(item)
            findNavController().navigate(action)
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

        // registering for page change callback
        binding.viewPager.registerOnPageChangeCallback(
            object : ViewPager2.OnPageChangeCallback() {

                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)

                    //update the image number textview
                    binding.imageNumber.text =
                        "${position + 1} / ${viewModel.imageSlider.value?.size}"
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
