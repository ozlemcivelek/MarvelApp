package com.example.marvelmovieapp.ui.home.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.navArgs
import com.example.marvelmovieapp.R
import com.example.marvelmovieapp.database.model.SavedItem
import com.example.marvelmovieapp.databinding.FragmentHomeDetailBinding
import com.example.marvelmovieapp.ui.MainViewModel
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeDetailFragment : Fragment() {

    private var _binding: FragmentHomeDetailBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<HomeDetailFragmentArgs>()

    private lateinit var item: SavedItem
    var isExist = false

    private val viewModel: MainViewModel by activityViewModels()
    private val detailViewModel: HomeDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeDetailBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        item = SavedItem(
            itemId = args.item.id,
            type = args.item.type,
            title = args.item.imageTitle,
            description = args.item.description,
            imageUrl = args.item.imageUrl
        )
        actionBarMenuProvider()

        viewModel.setTitle(item.title)
        binding.descriptionTextView.text = item.description
        Picasso.get()
            .load(item.imageUrl)
            .error(R.drawable.baseline_error_24)
            .into(binding.detailImageView)
    }

    private fun actionBarMenuProvider() {
        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.action_bar_menu, menu)

                val favItem = menu.findItem(R.id.action_fav)
                setInitialFavIconColor(favItem)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.action_fav -> {
                        if (isExist) {
                            deleteItem(item.itemId)
                        } else {
                            saveItem(item)
                        }
                        updateFavIconColor(menuItem, !isExist)
                        true
                    }

                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun setInitialFavIconColor(menuItem: MenuItem) {
        detailViewModel.checkIfItemExists(item.itemId) { isExist ->
            updateFavIconColor(menuItem, isExist)
        }
    }

    private fun updateFavIconColor(menuItem: MenuItem, isFav: Boolean) {
        this.isExist = isFav
        val color = if (isFav) {
            android.R.color.holo_red_dark // Favoriye eklenmişse kırmızı renk
        } else {
            android.R.color.white // Favoriye eklenmemişse beyaz renk
        }
        menuItem.icon?.setTint(ContextCompat.getColor(requireContext(), color))
    }

    private fun saveItem(item: SavedItem) {
        detailViewModel.insertItem(item)
    }

    private fun deleteItem(id: Int) {
        detailViewModel.deleteItem(id)
    }
}
