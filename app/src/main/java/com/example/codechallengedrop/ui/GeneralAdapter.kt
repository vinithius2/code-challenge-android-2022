package com.example.codechallengedrop.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.codechallengedrop.R
import com.example.codechallengedrop.data.response.DefaultValueUnit
import com.example.codechallengedrop.databinding.IngredientsViewholderBinding

class GeneralAdapter(
    private val dataSet: List<Pair<String, DefaultValueUnit>>,
    private val tag: String
) : RecyclerView.Adapter<GeneralAdapter.MethodViewHolder>() {

    private lateinit var view: View
    var onCallBackClickBalance: ((tag: String, position: Int, value: Double, unit: String) -> Unit)? =
        null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MethodViewHolder {
        val binding =
            IngredientsViewholderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        view = binding.root
        return MethodViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MethodViewHolder, position: Int) {
        holder.bind(dataSet[position].first, dataSet[position].second, position)
    }

    override fun getItemCount() = dataSet.size

    fun updateStatus(status: Boolean, position: Int) {
        dataSet[position].second.status = status
    }

    inner class MethodViewHolder(
        private val binding: IngredientsViewholderBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(method: String, valueUnit: DefaultValueUnit, position: Int) {
            binding.ingredient.text = method
            binding.buttonBalance.setOnClickListener {
                onCallBackClickBalance?.invoke(
                    tag,
                    position,
                    valueUnit.value,
                    valueUnit.unit
                )
            }
            if (valueUnit.status) {
                binding.buttonBalance.text = view.context.getText(R.string.done)
            }
        }
    }
}
