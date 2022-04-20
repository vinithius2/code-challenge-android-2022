package com.example.codechallengedrop.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.codechallengedrop.data.response.Malt
import com.example.codechallengedrop.databinding.IngredientsViewholderBinding

class MaltsAdapter(
    private val dataSet: List<Malt>
) : RecyclerView.Adapter<MaltsAdapter.MaltsViewHolder>() {

    var onCallBackClickBalance: ((value: Double, unit: String) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MaltsViewHolder {
        val binding =
            IngredientsViewholderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MaltsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MaltsViewHolder, position: Int) {
        holder.bind(dataSet[position])
    }

    override fun getItemCount() = dataSet.size

    inner class MaltsViewHolder(
        private val binding: IngredientsViewholderBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(malt: Malt) {
            binding.ingredient.text = "${malt.name} (${malt.amount.value} ${malt.amount.unit})"
            binding.buttonBalance.setOnClickListener {
                onCallBackClickBalance?.invoke(malt.amount.value, malt.amount.unit)
            }
        }
    }
}
