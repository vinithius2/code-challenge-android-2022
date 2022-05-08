package com.example.codechallengedrop.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.codechallengedrop.R
import com.example.codechallengedrop.databinding.FragmentBalanceBinding
import com.example.codechallengedrop.extension.capitalize
import org.koin.android.viewmodel.ext.android.sharedViewModel


class BalanceFragment : Fragment() {

    private val viewModel by sharedViewModel<BeerViewModel>()
    private lateinit var binding: FragmentBalanceBinding

    override fun onDestroy() {
        super.onDestroy()
        viewModel.resetValuesBalance()
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
            textValueBalance.text = "${viewModel.valueBalance} ${viewModel.unit.capitalize()}"
            progress.saveDataStream(viewModel.getSimulationDataStream())
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
                    viewModel.tagModel,
                    viewModel.position
                )
                requireActivity().onBackPressed()
            }
        }
    }
}
