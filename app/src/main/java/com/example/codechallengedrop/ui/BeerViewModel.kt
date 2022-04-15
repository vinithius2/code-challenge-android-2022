package com.example.codechallengedrop.ui

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

    fun getBeerList() {
        CoroutineScope(Dispatchers.IO).launch {
            _beerListLoading.postValue(true)
            try {
                val value = beerRepositoryData.beerList()
                _beerList.postValue(value)
            } catch (e: Exception) {
                _beerListError.postValue(true)
                _beerListLoading.postValue(false)
            }
            _beerListLoading.postValue(false)
        }
    }

    fun getBeerDetail(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            CoroutineScope(Dispatchers.IO).launch {
                _beerDetailLoading.postValue(true)
                try {
                    val value = beerRepositoryData.beerDetail(id)
                    _beerDetail.postValue(value)
                } catch (e: Exception) {
                    _beerDetailError.postValue(true)
                    _beerDetailLoading.postValue(false)
                }
                _beerDetailLoading.postValue(false)
            }
        }
    }

}