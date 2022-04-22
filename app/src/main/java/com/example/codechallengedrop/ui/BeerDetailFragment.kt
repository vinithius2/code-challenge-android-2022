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
import com.example.codechallengedrop.extension.getNavigationResult
import com.squareup.picasso.Picasso
import org.koin.android.viewmodel.ext.android.viewModel


class BeerDetailFragment : Fragment() {

    private val viewModel: BeerViewModel by viewModel()
    private lateinit var binding: FragmentBeerDetailBinding
    private lateinit var hopsAdapter: HopsAdapter
    private lateinit var maltsAdapter: MaltsAdapter
    private lateinit var methodsAdapter: MethodsAdapter
    private var id: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val bundle = this.arguments
        if (bundle != null) {
            id = bundle.getInt("id_beer", defaultValue)
            with(viewModel) {
                id?.let { getBeerDetail(it) }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBeerDetailBinding.inflate(inflater)
        binding.buttonNetworkAgain.setOnClickListener {
            id?.let { viewModel.getBeerDetail(it) }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observerLoading()
        observerError()
        observerBeerDetail()
    }

    private fun observerBeerDetail() {
        viewModel.beerDetail.observe(viewLifecycleOwner) { beer ->
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
            onCallBackClickBalance = { tag, position, value, unit ->
                navigationBalance(value, unit, tag, position)
            }
        }
        getNavigationResult(HOPS)?.observe(viewLifecycleOwner) {
            hopsAdapter.updateStatus(true, it)
            hopsAdapter.notifyItemChanged(it)
        }
    }

    private fun adapterMalts(malts: List<Malt>) {
        val layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.recyclerViewMalts.layoutManager = layoutManager
        maltsAdapter = MaltsAdapter(malts)
        binding.recyclerViewMalts.adapter = maltsAdapter.apply {
            onCallBackClickBalance = { tag, position, value, unit ->
                navigationBalance(value, unit, tag, position)
            }
        }
        getNavigationResult(MALTS)?.observe(viewLifecycleOwner) {
            maltsAdapter.updateStatus(true, it)
            maltsAdapter.notifyItemChanged(it)
        }
    }

    private fun adapterMethods(methods: Method) {
        val layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.recyclerViewMethod.layoutManager = layoutManager
        methodsAdapter = MethodsAdapter(methods.getNewFormat())
        binding.recyclerViewMethod.adapter = methodsAdapter.apply {
            onCallBackClickBalance = { tag, position, value, unit ->
                navigationBalance(value, unit, tag, position)
            }
        }
        getNavigationResult(METHOD)?.observe(viewLifecycleOwner) {
            methodsAdapter.updateStatus(true, it)
            methodsAdapter.notifyItemChanged(it)
        }
    }

    private fun observerLoading() {
        viewModel.beerDetailLoading.observe(viewLifecycleOwner) { loading ->
            if (loading) {
                binding.progressBar.visibility = View.VISIBLE
                binding.imageError.visibility = View.GONE
                binding.buttonNetworkAgain.visibility = View.GONE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        }
    }

    private fun observerError() {
        viewModel.beerDetailError.observe(viewLifecycleOwner) { error ->
            if (error) {
                binding.imageError.visibility = View.VISIBLE
                binding.buttonNetworkAgain.visibility = View.VISIBLE
            } else {
                binding.imageError.visibility = View.GONE
                binding.buttonNetworkAgain.visibility = View.GONE
            }
        }
    }

    private fun navigationBalance(value: Double, unit: String, tag: String, position: Int) {
        val bundle = Bundle()
        bundle.putDouble("value", value)
        bundle.putString("unit", unit)
        bundle.putString("tag", tag)
        bundle.putInt("position", position)
        findNavController().navigate(
            R.id.action_beerDetailFragment_to_balanceFragment,
            bundle
        )
    }

    companion object {
        const val METHOD: String = "METHOD"
        const val HOPS: String = "HOPS"
        const val MALTS: String = "MALTS"
    }
}
