package com.example.codechallengedrop.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.codechallengedrop.databinding.IngredientsViewholderBinding

class MethodsAdapter(
    private val dataSet: List<String>
) : RecyclerView.Adapter<MethodsAdapter.MethodViewHolder>() {

    var onCallBackClickBalance: (() -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MethodViewHolder {
        val binding =
            IngredientsViewholderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MethodViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MethodViewHolder, position: Int) {
        holder.bind(dataSet[position])
    }

    override fun getItemCount() = dataSet.size

    inner class MethodViewHolder(
        private val binding: IngredientsViewholderBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(method: String) {
            binding.ingredient.text = method
            binding.buttonBalance.setOnClickListener {
                onCallBackClickBalance?.invoke()
            }
        }
    }
}
