package com.example.marvelmovieapp.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.marvelmovieapp.adapter.EventsRecyclerViewAdapter
import com.example.marvelmovieapp.adapter.ImageViewPagerAdapter
import com.example.marvelmovieapp.databinding.FragmentHomeBinding
import com.example.marvelmovieapp.models.MyEvents
import com.example.marvelmovieapp.models.SliderModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!


    private val viewModel by viewModels<HomeViewModel>()

    //private val imageUrlList: ArrayList<String> = ArrayList()
    private val imageSlider: ArrayList<SliderModel> = ArrayList()
    private val eventImage: ArrayList<MyEvents> = ArrayList()

    private lateinit var imageViewPagerAdapter: ImageViewPagerAdapter
    private var eventsRecyclerViewAdapter = EventsRecyclerViewAdapter()

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

        eventsRecyclerViewAdapter.onItemClicked = {
            val action = HomeFragmentDirections.actionHomeFragmentToHomeDetail(
                title = eventImage[it].imageTitle,
                imageUrl = eventImage[it].imageUrl,
                description = eventImage[it].description
            )
            view.findNavController().navigate(action)
            Log.d("Home", "on clicked.")
        }

        binding.eventsRecyclerView.adapter = eventsRecyclerViewAdapter

        observeAndHandleComicResponse()
        observeAndHandleEventsResponse()
    }

    private fun observeAndHandleComicResponse() {
        viewModel.imageSlider.observe(viewLifecycleOwner) { comic ->
            if (comic.isEmpty()) return@observe
            imageSlider.addAll(comic)
            //initializing the adapter
            imageViewPagerAdapter = ImageViewPagerAdapter(imageSlider)
            setUpViewPager()
        }
    }

    private fun observeAndHandleEventsResponse() {
        viewModel.events.observe(viewLifecycleOwner) { events ->
            if (events.isEmpty()) return@observe
            eventImage.addAll(events)
            eventsRecyclerViewAdapter.setItems(eventImage)
        }

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
                    binding.imageNumber.text = "${position + 1} / ${imageSlider.size}"
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