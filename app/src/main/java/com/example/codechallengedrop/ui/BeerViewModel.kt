package com.example.codechallengedrop.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.codechallengedrop.R
import com.example.codechallengedrop.data.repository.BeerRepositoryData
import com.example.codechallengedrop.data.response.Beer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BeerViewModel(
    private val beerRepositoryData: BeerRepositoryData
) : ViewModel() {

    private val _beerList = MutableLiveData<List<Beer>>()
    val beerList: LiveData<List<Beer>>
        get() = _beerList

    private val _beerDetail = MutableLiveData<Beer>()
    val beerDetail: LiveData<Beer>
        get() = _beerDetail

    private val _beerListLoading = MutableLiveData<Boolean>()
    val beerListLoading: LiveData<Boolean>
        get() = _beerListLoading

    private val _beerDetailLoading = MutableLiveData<Boolean>()
    val beerDetailLoading: LiveData<Boolean>
        get() = _beerDetailLoading

    private val _beerListError = MutableLiveData<Boolean>().apply { postValue(false) }
    val beerListError: LiveData<Boolean>
        get() = _beerListError

    private val _beerDetailError = MutableLiveData<Boolean>().apply { postValue(false) }
    val beerDetailError: LiveData<Boolean>
        get() = _beerDetailError

    private val _beerStringError = MutableLiveData<Int>()
    val beerStringError: LiveData<Int>
        get() = _beerStringError

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
            _beerListLoading.postValue(true)
            try {
                beerRepositoryData.beerList().run {
                    _beerList.postValue(this)
                }
            } catch (e: Exception) {
                getError(R.string.unknown_error)
            }
            _beerListLoading.postValue(false)
        }
    }

    fun getResponseToBeerDetail(id: Int) {
        _beerDetailLoading.postValue(true)
        try {
            _beerList.value?.find { it.id == id }?.run {
                _beerDetail.postValue(this)
            }
        } catch (e: Exception) {
            getError(R.string.unknown_error)
        }
        _beerDetailLoading.postValue(false)
    }

    private fun getError(msgString: Int) {
        _beerStringError.postValue(msgString)
        _beerListError.postValue(true)
        _beerListLoading.postValue(false)
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
