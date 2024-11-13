package com.example.marvelmovieapp.ui.home.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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

        // Kaydedilen öğeleri gözlemleyin
        /*savedItemViewModel.allItems.observe(viewLifecycleOwner, Observer { items ->
            // Buraya kaydedilen öğeleri kullanarak istediğiniz işlemleri yapabilirsiniz
        })*/

        actionBarMenuProvider()

        viewModel.setTitle(args.title)
        binding.descriptionTextView.text = args.description
        Picasso.get()
            .load(args.imageUrl)
            .error(R.drawable.baseline_error_24)
            .into(binding.detailImageView)
    }

    private fun actionBarMenuProvider() {
        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.action_bar_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.action_fav -> {
                        // Detay sayfasında fav ikonuna tıklanınca:
                        saveItem(
                            args.title,
                            args.description,
                            args.imageUrl
                        )
                        Toast.makeText(requireContext(), "Favoriye Eklendi", Toast.LENGTH_SHORT)
                            .show()

                        //findNavController().navigate(R.id.action_homeDetail_to_myLibraryFragment)

                        true
                    }

                    else -> {
                        Toast.makeText(requireContext(), "Favoriye eklenemedi", Toast.LENGTH_SHORT)
                            .show()

                        false
                    }
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun saveItem(title: String, description: String, imageUrl: String) {
        //Create Item Object
        val item = SavedItem(title = title, description = description, imageUrl = imageUrl)
        //Add Data to Database
        detailViewModel.insertItem(item)
    }
}
