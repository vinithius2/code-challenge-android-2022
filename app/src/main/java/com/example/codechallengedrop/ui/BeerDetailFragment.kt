package com.example.codechallengedrop.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.codechallengedrop.R
import com.example.codechallengedrop.databinding.FragmentBeerDetailBinding
import com.example.codechallengedrop.extension.getNavigationResult
import com.example.codechallengedrop.extension.getNewFormat
import com.squareup.picasso.Picasso
import org.koin.android.viewmodel.ext.android.sharedViewModel


class BeerDetailFragment : Fragment() {

    private val viewModel by sharedViewModel<BeerViewModel>()
    private lateinit var binding: FragmentBeerDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        with(viewModel) {
            getResponseToBeerDetail(idBeer)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBeerDetailBinding.inflate(inflater)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
        binding.layoutError.buttonNetworkAgain.setOnClickListener {
            with(viewModel) {
                getResponseToBeerDetail(idBeer)
            }
        }
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.resetIdBeer()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observerBeerDetail()
    }

    private fun observerBeerDetail() {
        viewModel.beerDetail.observe(viewLifecycleOwner) { beer ->
            (activity as MainActivity).supportActionBar?.title = beer.name
            binding.colorAbvStatus.setData(beer.abv)
            Picasso.get().load(beer.image_url).into(binding.imageBeer)
            with(binding) {
                adapterGeneral(
                    GeneralAdapter(beer.ingredients.hops.getNewFormat(), HOPS),
                    recyclerViewHops,
                    HOPS
                )
                adapterGeneral(
                    GeneralAdapter(beer.ingredients.malt.getNewFormat(), MALTS),
                    recyclerViewMalts,
                    MALTS
                )
                adapterGeneral(
                    GeneralAdapter(beer.method.getNewFormat(), METHOD),
                    recyclerViewMethod,
                    METHOD
                )
            }
        }
    }

    private fun adapterGeneral(adapter: GeneralAdapter, recyclerView: RecyclerView, tag: String) {
        val layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter.apply {
            onCallBackClickBalance = { tag, position, value, unit ->
                navigationBalance(value, unit, tag, position)
            }
        }
        getNavigationResult(tag)?.observe(viewLifecycleOwner) {
            adapter.updateStatus(true, it)
            adapter.notifyItemChanged(it)
        }
    }

    private fun navigationBalance(value: Double, unit: String, tag: String, position: Int) {
        viewModel.setValuesBalance(value, unit, tag, position)
        findNavController().navigate(
            R.id.action_beerDetailFragment_to_balanceFragment
        )
    }

    companion object {
        const val METHOD: String = "METHOD"
        const val HOPS: String = "HOPS"
        const val MALTS: String = "MALTS"
    }
}
