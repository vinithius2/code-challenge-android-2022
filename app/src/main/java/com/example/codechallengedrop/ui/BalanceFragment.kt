package com.example.codechallengedrop.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.codechallengedrop.databinding.FragmentBalanceBinding
import com.example.codechallengedrop.extension.capitalize

class BalanceFragment : Fragment() {

    private lateinit var binding: FragmentBalanceBinding
    private var value: Double = 0.0
    private var unit: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBalanceBinding.inflate(inflater)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = this.arguments
        if (bundle != null) {
            value = bundle.getDouble("value", 0.0)
            unit = bundle.getString("unit", "")
        }
    }

    override fun onStart() {
        super.onStart()
        with(binding) {
            binding.textValueBalance.text = "$value ${unit.capitalize()}"
            buttonDone.setOnClickListener {
                progress.apply {
                    onCallBackValue = {
                        textValueRealtime.text = it.toString()
                    }
                }
                progress.animateProgress(simulationDataStream(value.toInt()))
            }
        }
    }

    /**
     * This function only simulates a data stream to test the balance screen.
     */
    private fun simulationDataStream(lastValue: Int): List<Pair<Int, Int>> {
        val time = mutableListOf<Pair<Int, Int>>()
        time.add(Pair(TIMESTAMP_VALUE_KEY, lastValue))
        val changes = (COUNT_MIN..COUNT_MAX).random()
        for (i in 0..changes) {
            val weight = (0..WEIGHT_MAX).random()
            val timestamp = (0..TIMESTAMP_MAX).random()
            time.add(Pair(timestamp, weight))
        }
        Log.i("BALANCE", time.toString())
        return time.toList().reversed()
    }

    companion object {
        const val COUNT_MIN: Int = 0
        const val COUNT_MAX: Int = 1
        const val WEIGHT_MAX: Int = 8000
        const val TIMESTAMP_VALUE_KEY: Int = 10000
        const val TIMESTAMP_MAX: Int = 10000
    }

}