package com.example.codechallengedrop.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.codechallengedrop.databinding.FragmentBeerListBinding
import org.koin.android.viewmodel.ext.android.viewModel


class BeerListFragment : Fragment() {

    private val viewModel: BeerViewModel by viewModel()
    private lateinit var binding: FragmentBeerListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observerBeerList()
        with(viewModel) {
            getBeerList()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBeerListBinding.inflate(inflater)
        return binding.root
    }

    private fun observerBeerList() {
        viewModel.beerList.observe(this) {
            Log.i("OK", "observerBeerList")
        }
    }

}