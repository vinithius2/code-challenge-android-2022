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
import org.koin.android.viewmodel.ext.android.sharedViewModel


class BeerListFragment : Fragment() {

    private val viewModel by sharedViewModel<BeerViewModel>()
    private lateinit var binding: FragmentBeerListBinding
    private lateinit var beerListAdapter: BeerListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(viewModel) {
            getResponseToBeerList()
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
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
        binding.layoutError.buttonNetworkAgain.setOnClickListener {
            viewModel.getResponseToBeerList()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observerBeerList()
    }

    private fun observerBeerList() {
        val layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.recyclerViewBeerList.layoutManager = layoutManager
        viewModel.beerList.observe(viewLifecycleOwner) { beers ->
            beerListAdapter = BeerListAdapter(beers)
            binding.recyclerViewBeerList.adapter = beerListAdapter.apply {
                onCallBackClickDetail = { id ->
                    viewModel.setIdBeer(id)
                    findNavController().navigate(
                        R.id.action_beerListFragment_to_beerDetailFragment,
                    )
                }
            }
        }
    }
}
