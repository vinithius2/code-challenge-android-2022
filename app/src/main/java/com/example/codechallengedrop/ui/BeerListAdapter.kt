package com.example.codechallengedrop.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.codechallengedrop.data.response.Beer
import com.example.codechallengedrop.databinding.BeerViewholderBinding
import com.squareup.picasso.Picasso

class BeerListAdapter(
    private val dataSet: List<Beer>
) : RecyclerView.Adapter<BeerListAdapter.BeerListViewHolder>() {

    var onCallBackClickDetail: ((id: Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeerListViewHolder {
        val binding = BeerViewholderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BeerListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BeerListViewHolder, position: Int) {
        holder.bind(dataSet[position])
    }

    override fun getItemCount() = dataSet.size

    inner class BeerListViewHolder(
        private val binding: BeerViewholderBinding
    ) : RecyclerView.ViewHolder(binding.root){

        fun bind(beer: Beer) {
            binding.nameBeer.text = beer.name
            binding.abvBeer.text = "${beer.abv}%"
            binding.colorAbvStatus.setData(beer.abv)
            Picasso.get().load(beer.image_url).into(binding.imageBeer)
            binding.cardViewHolder.setOnClickListener {
                onCallBackClickDetail?.invoke(beer.id)
            }
        }

    }

}