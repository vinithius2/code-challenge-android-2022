package com.example.codechallengedrop.ui

import android.R.attr.defaultValue
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.codechallengedrop.databinding.FragmentBeerDetailBinding
import com.squareup.picasso.Picasso
import org.koin.android.ext.android.bind
import org.koin.android.viewmodel.ext.android.viewModel


class BeerDetailFragment : Fragment() {

    private val viewModel: BeerViewModel by viewModel()
    private lateinit var binding: FragmentBeerDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        observerLoading()
        observerError()
        observerBeerDetail()
        val bundle = this.arguments
        if (bundle != null) {
            val id = bundle.getInt("id_beer", defaultValue)
            with(viewModel) {
                getBeerDetail(id)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBeerDetailBinding.inflate(inflater)
        return binding.root
    }

    private fun observerBeerDetail() {
        viewModel.beerDetail.observe(this) { beer ->
            binding.cardViewHolder.visibility = View.VISIBLE
            (activity as MainActivity).supportActionBar?.title = beer.name
            binding.abvBeer.text = "${beer.abv}%"
            binding.colorAbvStatus.setData(beer.abv)
            binding.descriptionBeer.text = beer.description
            Picasso.get().load(beer.image_url).into(binding.imageBeer)
        }
    }

    private fun observerLoading() {
        viewModel.beerDetailLoading.observe(this) { loading ->
            if (loading) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        }
    }

    private fun observerError() {
        viewModel.beerDetailError.observe(this) { error ->
            if (error) {
                binding.imageError.visibility = View.VISIBLE
            } else {
                binding.imageError.visibility = View.GONE
            }
        }
    }
}