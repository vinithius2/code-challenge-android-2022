package com.example.codechallengedrop.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.codechallengedrop.data.response.Hops
import com.example.codechallengedrop.databinding.IngredientsViewholderBinding

class HopsAdapter(
    private val dataSet: List<Hops>
) : RecyclerView.Adapter<HopsAdapter.HopsViewHolder>() {

    var onCallBackClickBalance: (() -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HopsViewHolder {
        val binding =
            IngredientsViewholderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HopsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HopsViewHolder, position: Int) {
        holder.bind(dataSet[position])
    }

    override fun getItemCount() = dataSet.size

    inner class HopsViewHolder(
        private val binding: IngredientsViewholderBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(hops: Hops) {
            binding.ingredient.text = "${hops.name} (${hops.amount.value} ${hops.amount.unit})"
            binding.buttonBalance.setOnClickListener {
                onCallBackClickBalance?.invoke()
            }
        }
    }

}