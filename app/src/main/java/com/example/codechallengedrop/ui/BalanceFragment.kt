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
    private var mTagModel: String = ""
    private var mUnit: String = ""
    private var mPosition: Int = 0
    private var mValue: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(viewModel) {
            mTagModel = tagModel
            mUnit = unit
            mPosition = position
            mValue = valueBalance
        }
    }

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
            textValueBalance.text = "$mValue ${mUnit.capitalize()}"
            progress.saveDataStream(viewModel.getSimulationDataStream(mValue.toInt()))
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
                    mTagModel,
                    mPosition
                )
                requireActivity().onBackPressed()
            }
        }
    }
}
