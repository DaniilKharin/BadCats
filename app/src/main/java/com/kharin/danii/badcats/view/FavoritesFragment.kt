package com.kharin.danii.badcats.view

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.kharin.danii.badcats.R
import com.kharin.danii.badcats.databinding.FavoritesFragmentBinding
import com.kharin.danii.badcats.viewmodel.ImagesViewModel

class FavoritesFragment : Fragment() {

    companion object {
        fun newInstance() = FavoritesFragment()
    }

    private lateinit var viewModel: ImagesViewModel
    private lateinit var binding: FavoritesFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FavoritesFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ImagesViewModel::class.java)
        (activity as MainActivity).appComponent.inject(viewModel)
        binding.viewModel = viewModel
        viewModel.initFavorites()
    }

}
