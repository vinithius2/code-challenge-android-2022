package com.example.codechallengedrop.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.codechallengedrop.R
import com.example.codechallengedrop.data.response.Hops
import com.example.codechallengedrop.databinding.IngredientsViewholderBinding

class HopsAdapter(
    private val dataSet: List<Hops>
) : RecyclerView.Adapter<HopsAdapter.HopsViewHolder>() {

    private lateinit var view: View
    var onCallBackClickBalance: ((tag: String, position: Int, value: Double, unit: String) -> Unit)? =
        null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HopsViewHolder {
        val binding =
            IngredientsViewholderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        view = binding.root
        return HopsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HopsViewHolder, position: Int) {
        holder.bind(dataSet[position], position)
    }

    override fun getItemCount() = dataSet.size

    fun updateStatus(status: Boolean, position: Int) {
        dataSet[position].status = status
    }

    inner class HopsViewHolder(
        private val binding: IngredientsViewholderBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(hops: Hops, position: Int) {
            binding.ingredient.text = "${hops.name} (${hops.amount.value} ${hops.amount.unit})"
            binding.buttonBalance.setOnClickListener {
                onCallBackClickBalance?.invoke(
                    BeerDetailFragment.HOPS,
                    position,
                    hops.amount.value,
                    hops.amount.unit
                )
            }
            if (hops.status) {
                binding.buttonBalance.text = view.context.getText(R.string.done)
            }
        }
    }

}
