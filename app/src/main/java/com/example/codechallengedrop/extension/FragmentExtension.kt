package com.example.codechallengedrop.extension

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

/**
 * Save result when go back to fragment.
 */
fun Fragment.getNavigationResult(key: String = "result") =
    findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<Int>(key)
