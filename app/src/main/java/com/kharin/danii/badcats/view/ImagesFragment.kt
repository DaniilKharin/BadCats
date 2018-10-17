package com.kharin.danii.badcats.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.kharin.danii.badcats.R
import com.kharin.danii.badcats.databinding.ImagesFragmentBinding
import com.kharin.danii.badcats.viewmodel.ImagesViewModel

class ImagesFragment : Fragment() {

    private var binding: ImagesFragmentBinding? = null

    companion object {
        fun newInstance() = ImagesFragment()
        @JvmStatic
        @BindingAdapter("imageUrl")
        fun ImageView.loadFromUrl(url: String) {
            Glide.with(this)
                .load(url)
                .apply(
                    RequestOptions()
                        .placeholder(R.drawable.ic_loading)
                        .error(R.drawable.ic_err_img)
                        .centerCrop()
                )
                .into(this)
        }
        val visibleThreshold = 3

        @JvmStatic
        @BindingAdapter("loadListener")
        fun RecyclerView.loadFromUrl(listener: ImagesViewModel.LoadNextPageListener) {
            this.addOnScrollListener(object : RecyclerView.OnScrollListener(){

                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (recyclerView.layoutManager == null) return
                    var linearLayoutManager = (recyclerView.layoutManager as LinearLayoutManager)
                    val totalItemCount = linearLayoutManager.itemCount
                    val lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition()
                    if (totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                       listener.loadNextPage()
                    }
                }})
        }
    }

    private lateinit var viewModel: ImagesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ImagesFragmentBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ImagesViewModel::class.java)
        (activity as MainActivity).appComponent.inject(viewModel)
        binding?.viewModel = viewModel
        viewModel.init()
    }


}
