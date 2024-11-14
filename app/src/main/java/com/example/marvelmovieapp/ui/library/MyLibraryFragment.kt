package com.example.marvelmovieapp.ui.library

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.marvelmovieapp.adapter.HomeItemAdapter
import com.example.marvelmovieapp.databinding.FragmentMyLibraryBinding
import com.example.marvelmovieapp.models.HomeItem
import com.example.marvelmovieapp.models.ItemType
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyLibraryFragment : Fragment() {

    private var _binding: FragmentMyLibraryBinding? = null
    private val binding get() = _binding!!

    private val myLibraryViewModel: MyLibraryViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMyLibraryBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeAndHandleResponse(ItemType.EVENTS, myLibraryViewModel.event , binding.eventsRecyclerView)
        observeAndHandleResponse(ItemType.COMICS, myLibraryViewModel.comic,binding.comicsRecyclerView)
        observeAndHandleResponse(ItemType.CREATORS, myLibraryViewModel.creator,binding.creatorsRecyclerView)
        observeAndHandleResponse(ItemType.CHARACTERS, myLibraryViewModel.character,binding.charactersRecyclerView)

    }

    private fun observeAndHandleResponse(
        type: ItemType,
        liveData: MutableLiveData<List<HomeItem>>,
        recyclerViewAdapter: RecyclerView)
    {
        val adapter = HomeItemAdapter()
        recyclerViewAdapter.adapter = adapter

        myLibraryViewModel.getAllItems(type.toString())
        liveData.observe(viewLifecycleOwner) {
            adapter.setItems(it)
        }
    }
}