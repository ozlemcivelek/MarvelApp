package com.example.marvelmovieapp.ui.library

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.marvelmovieapp.databinding.FragmentMyLibraryBinding

class MyLibraryFragment : Fragment() {

    private var _binding: FragmentMyLibraryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMyLibraryBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }
}