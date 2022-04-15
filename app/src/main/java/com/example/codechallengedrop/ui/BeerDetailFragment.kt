package com.example.codechallengedrop.ui

import android.R.attr.defaultValue
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.codechallengedrop.databinding.FragmentBeerDetailBinding
import org.koin.android.viewmodel.ext.android.viewModel


class BeerDetailFragment : Fragment() {

    private val viewModel: BeerViewModel by viewModel()
    private lateinit var binding: FragmentBeerDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        viewModel.beerDetail.observe(this) {
            Log.i("OK", "observerBeerList")
        }
    }
}