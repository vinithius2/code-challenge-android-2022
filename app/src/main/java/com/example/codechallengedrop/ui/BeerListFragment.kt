package com.example.codechallengedrop.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.codechallengedrop.R
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

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        (activity as MainActivity).supportActionBar?.title = getString(R.string.app_name)
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
        // TODO: To analyze
        observerLoading()
        observerError()
        observerBeerList()
    }

    private fun observerBeerList() {
        val layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.recyclerViewBeerList.layoutManager = layoutManager
        viewModel.beerList.observe(this) { beers ->
            binding.recyclerViewBeerList.visibility = View.VISIBLE
            beerListAdapter = BeerListAdapter(beers)
            binding.recyclerViewBeerList.adapter = beerListAdapter.apply {
                onCallBackClickDetail = { id ->
                    val bundle = Bundle()
                    bundle.putInt("id_beer", id)
                    findNavController().navigate(
                        R.id.action_beerListFragment_to_beerDetailFragment,
                        bundle
                    )
                }
            }
        }
    }

    private fun observerLoading() {
        viewModel.beerListLoading.observe(this) { loading ->
            if (loading) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        }
    }

    private fun observerError() {
        viewModel.beerListError.observe(this) { error ->
            if (error) {
                binding.imageError.visibility = View.VISIBLE
            } else {
                binding.imageError.visibility = View.GONE
            }
        }
    }
}
