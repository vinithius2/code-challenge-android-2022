package com.example.codechallengedrop.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.codechallengedrop.R
import com.example.codechallengedrop.data.response.Malt
import com.example.codechallengedrop.databinding.IngredientsViewholderBinding

class MaltsAdapter(
    private val dataSet: List<Malt>
) : RecyclerView.Adapter<MaltsAdapter.MaltsViewHolder>() {

    private lateinit var view: View
    var onCallBackClickBalance: ((tag: String, position: Int, value: Double, unit: String) -> Unit)? =
        null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MaltsViewHolder {
        val binding =
            IngredientsViewholderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        view = binding.root
        return MaltsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MaltsViewHolder, position: Int) {
        holder.bind(dataSet[position], position)
    }

    override fun getItemCount() = dataSet.size

    fun updateStatus(status: Boolean, position: Int) {
        dataSet[position].status = status
    }

    inner class MaltsViewHolder(
        private val binding: IngredientsViewholderBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(malt: Malt, position: Int) {
            binding.ingredient.text = "${malt.name} (${malt.amount.value} ${malt.amount.unit})"
            binding.buttonBalance.setOnClickListener {
                onCallBackClickBalance?.invoke(
                    BeerDetailFragment.MALTS,
                    position,
                    malt.amount.value,
                    malt.amount.unit
                )
            }
            if (malt.status) {
                binding.buttonBalance.text = view.context.getText(R.string.done)
            }
        }
    }
}
