package com.example.codechallengedrop.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.codechallengedrop.databinding.FragmentBalanceBinding

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
        binding.textValueBalance.text = "${value} ${unit}"
        binding.buttonDone.setOnClickListener {
            binding.progress.animateProgress()
        }
    }

}