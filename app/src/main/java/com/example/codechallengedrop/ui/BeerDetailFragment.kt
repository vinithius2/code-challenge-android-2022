package com.example.codechallengedrop.ui

import android.R.attr.defaultValue
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.codechallengedrop.R
import com.example.codechallengedrop.data.response.Hops
import com.example.codechallengedrop.data.response.Malt
import com.example.codechallengedrop.data.response.Method
import com.example.codechallengedrop.databinding.FragmentBeerDetailBinding
import com.squareup.picasso.Picasso
import org.koin.android.viewmodel.ext.android.viewModel


class BeerDetailFragment : Fragment() {

    private val viewModel: BeerViewModel by viewModel()
    private lateinit var binding: FragmentBeerDetailBinding
    private lateinit var hopsAdapter: HopsAdapter
    private lateinit var maltsAdapter: MaltsAdapter
    private lateinit var methodsAdapter: MethodsAdapter

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
            visibileCards()
            (activity as MainActivity).supportActionBar?.title = beer.name
            binding.abvBeer.text = "${beer.abv}%"
            binding.colorAbvStatus.setData(beer.abv)
            binding.descriptionBeer.text = beer.description
            Picasso.get().load(beer.image_url).into(binding.imageBeer)
            adapterHops(beer.ingredients.hops)
            adapterMalts(beer.ingredients.malt)
            adapterMethods(beer.method)
        }
    }

    private fun visibileCards() {
        binding.scrollDetailBeer.visibility = View.VISIBLE
    }

    private fun adapterHops(hops: List<Hops>) {
        val layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.recyclerViewHops.layoutManager = layoutManager
        hopsAdapter = HopsAdapter(hops)
        binding.recyclerViewHops.adapter = hopsAdapter.apply {
            onCallBackClickBalance = { value, unit ->
                navigationBalance(value, unit)
            }
        }
    }

    private fun adapterMalts(malts: List<Malt>) {
        val layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.recyclerViewMalts.layoutManager = layoutManager
        maltsAdapter = MaltsAdapter(malts)
        binding.recyclerViewMalts.adapter = maltsAdapter.apply {
            onCallBackClickBalance = { value, unit ->
                navigationBalance(value, unit)
            }
        }
    }

    private fun adapterMethods(methods: Method) {
        val layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.recyclerViewMethod.layoutManager = layoutManager
        methodsAdapter = MethodsAdapter(methods.getNewFormat())
        binding.recyclerViewMethod.adapter = methodsAdapter.apply {
            onCallBackClickBalance = { value, unit ->
                navigationBalance(value, unit)
            }
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

    private fun navigationBalance(value: Double, unit: String) {
        val bundle = Bundle()
        bundle.putDouble("value", value)
        bundle.putString("unit", unit)
        findNavController().navigate(
            R.id.action_beerDetailFragment_to_balanceFragment,
            bundle
        )
    }
}