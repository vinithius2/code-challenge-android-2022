package com.example.codechallengedrop.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.codechallengedrop.databinding.FragmentBeerListBinding
import org.koin.android.viewmodel.ext.android.viewModel


class BeerListFragment : Fragment() {

    private val viewModel: BeerViewModel by viewModel()
    private lateinit var binding: FragmentBeerListBinding
    private lateinit var beerListAdapter: BeerListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

    override fun onStart() {
        super.onStart()
        observerBeerList()
    }

    private fun observerBeerList() {
        val layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.recyclerViewBeerList.layoutManager = layoutManager
        viewModel.beerList.observe(this) { beers ->
            beerListAdapter = BeerListAdapter(beers)
            binding.recyclerViewBeerList.adapter = beerListAdapter
        }
    }

}