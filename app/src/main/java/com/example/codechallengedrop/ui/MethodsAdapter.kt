package com.example.codechallengedrop.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.codechallengedrop.data.response.DefaultValueUnit
import com.example.codechallengedrop.databinding.IngredientsViewholderBinding

class MethodsAdapter(
    private val dataSet: List<Pair<String, DefaultValueUnit>>
) : RecyclerView.Adapter<MethodsAdapter.MethodViewHolder>() {

    var onCallBackClickBalance: ((value: Double, unit: String) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MethodViewHolder {
        val binding =
            IngredientsViewholderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MethodViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MethodViewHolder, position: Int) {
        holder.bind(dataSet[position].first, dataSet[position].second)
    }

    override fun getItemCount() = dataSet.size

    inner class MethodViewHolder(
        private val binding: IngredientsViewholderBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(method: String, valueUnit: DefaultValueUnit) {
            binding.ingredient.text = method
            binding.buttonBalance.setOnClickListener {
                onCallBackClickBalance?.invoke(valueUnit.value, valueUnit.unit)
            }
        }
    }
}
