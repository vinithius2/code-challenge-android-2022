package com.example.codechallengedrop.ui

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.codechallengedrop.data.repository.BeerRepositoryData
import com.example.codechallengedrop.data.response.Beer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BeerViewModel(
    private val beerRepositoryData: BeerRepositoryData
) : ViewModel() {

    // Response

    private val _beerList = MutableLiveData<List<Beer>>()
    val beerList: LiveData<List<Beer>>
        get() = _beerList

    private val _beerDetail = MutableLiveData<Beer>()
    val beerDetail: LiveData<Beer>
        get() = _beerDetail

    // Visibility list

    private val _beerListContentsVisibility = MutableLiveData<Int>()
    val beerListContentsVisibility: LiveData<Int>
        get() = _beerListContentsVisibility

    private val _beerListLoadingVisibility = MutableLiveData<Int>()
    val beerListLoadingVisibility: LiveData<Int>
        get() = _beerListLoadingVisibility

    private val _beerListErrorVisibility = MutableLiveData<Int>().apply { postValue(View.GONE) }
    val beerListErrorVisibility: LiveData<Int>
        get() = _beerListErrorVisibility

    // Visibility detail

    private val _beerDetailContentsVisibility = MutableLiveData<Int>()
    val beerDetailContentsVisibility: LiveData<Int>
        get() = _beerDetailContentsVisibility

    private val _beerDetailLoadingVisibility = MutableLiveData<Int>()
    val beerDetailLoadingVisibility: LiveData<Int>
        get() = _beerDetailLoadingVisibility

    private val _beerDetailErrorVisibility = MutableLiveData<Int>().apply { postValue(View.GONE) }
    val beerDetailErrorVisibility: LiveData<Int>
        get() = _beerDetailErrorVisibility

    // Variables for fragments

    private var _idBeer: Int = 0
    val idBeer: Int
        get() = _idBeer

    private var _valueBalance: Double = 0.0
    val valueBalance: Double
        get() = _valueBalance

    private var _tagModel: String = ""
    val tagModel: String
        get() = _tagModel

    private var _unit: String = ""
    val unit: String
        get() = _unit

    private var _position: Int = 0
    val position: Int
        get() = _position

    fun setValuesBalance(value: Double, unit: String, tag: String, position: Int) {
        _valueBalance = value
        _unit = unit
        _tagModel = tag
        _position = position
    }

    fun setIdBeer(value: Int) {
        _idBeer = value
    }

    fun resetIdBeer() {
        _idBeer = 0
    }

    fun resetValuesBalance() {
        _valueBalance = 0.0
        _unit = ""
        _tagModel = ""
        _position = 0
    }

    fun getResponseToBeerList() {
        CoroutineScope(Dispatchers.IO).launch {
            _beerListLoadingVisibility.postValue(View.VISIBLE)
            _beerListErrorVisibility.postValue(View.GONE)
            kotlin.runCatching {
                beerRepositoryData.beerList().run {
                    _beerList.postValue(this)
                }
            }.onSuccess {
                getHideListError()
            }.onFailure {
                getShowListError()
            }
        }
    }

    fun getResponseToBeerDetail(id: Int) {
        _beerDetailLoadingVisibility.postValue(View.VISIBLE)
        _beerDetailErrorVisibility.postValue(View.GONE)
        try {
            _beerList.value?.find { it.id == id }?.run {
                _beerDetail.postValue(this)
            }
            getHideDetailError()
        } catch (e: Exception) {
            getShowDetailError()
        }
        _beerDetailLoadingVisibility.postValue(View.GONE)
    }

    private fun getShowListError() {
        _beerListErrorVisibility.postValue(View.VISIBLE)
        _beerListLoadingVisibility.postValue(View.GONE)
        _beerListContentsVisibility.postValue(View.GONE)
    }

    private fun getHideListError() {
        _beerListErrorVisibility.postValue(View.GONE)
        _beerListLoadingVisibility.postValue(View.GONE)
        _beerListContentsVisibility.postValue(View.VISIBLE)
    }

    private fun getShowDetailError() {
        _beerDetailErrorVisibility.postValue(View.VISIBLE)
        _beerDetailLoadingVisibility.postValue(View.GONE)
        _beerDetailContentsVisibility.postValue(View.GONE)
    }

    private fun getHideDetailError() {
        _beerDetailErrorVisibility.postValue(View.GONE)
        _beerDetailLoadingVisibility.postValue(View.GONE)
        _beerDetailContentsVisibility.postValue(View.VISIBLE)
    }

    /**
     * This function only simulates a data stream to test the balance screen.
     */
    fun getSimulationDataStream(lastValue: Int): List<Pair<Int, Int>> {
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
