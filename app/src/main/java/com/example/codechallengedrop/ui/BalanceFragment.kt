package com.example.codechallengedrop.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.codechallengedrop.R
import com.example.codechallengedrop.databinding.FragmentBalanceBinding
import com.example.codechallengedrop.extension.capitalize


class BalanceFragment : Fragment() {

    private lateinit var callback: OnBackPressedCallback
    private lateinit var binding: FragmentBalanceBinding
    private var tagModel: String = ""
    private var unit: String = ""
    private var position: Int = 0
    private var value: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = this.arguments
        if (bundle != null) {
            value = bundle.getDouble("value", 0.0)
            unit = bundle.getString("unit", "")
            position = bundle.getInt("position", 0)
            tagModel = bundle.getString("tag", "")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBalanceBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            textValueBalance.text = "$value ${unit.capitalize()}"
            progress.saveDataStream(simulationDataStream(value.toInt()))
            progress.apply {
                onCallBackValue = {
                    textValueRealtime.text = it.toString()
                }
                onCallBackFinish = {
                    buttonBalance.text = getString(R.string.again)
                    buttonBalance.isEnabled = true
                    buttonBalance.isClickable = true
                    buttonDone.visibility = View.VISIBLE
                }
            }
            buttonBalance.setOnClickListener {
                buttonBalance.text = getString(R.string.weighing)
                buttonBalance.isEnabled = false
                buttonBalance.isClickable = false
                buttonDone.visibility = View.GONE
                progress.animateProgress()
            }
            buttonDone.setOnClickListener {
                findNavController().previousBackStackEntry?.savedStateHandle?.set(
                    tagModel,
                    position
                )
                requireActivity().onBackPressed()
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
        return time.toList().reversed()
    }

    companion object {
        const val COUNT_MIN: Int = 2
        const val COUNT_MAX: Int = 8
        const val WEIGHT_MAX: Int = 1000
        const val TIMESTAMP_VALUE_KEY: Int = 4000
        const val TIMESTAMP_MAX: Int = 6000
    }
}
