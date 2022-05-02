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

    fun getBeerList() {
        CoroutineScope(Dispatchers.IO).launch {
            _beerListLoading.postValue(true)
            try {
                beerRepositoryData.beerList().run {
                    getTheResultByHttpCode(this.code()) {
                        _beerList.postValue(this.body())
                    }
                }
            } catch (e: Exception) {
                getError(R.string.unknown_error)
            }
            _beerListLoading.postValue(false)
        }
    }

    fun getBeerDetail(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            _beerDetailLoading.postValue(true)
            try {
                beerRepositoryData.beerDetail(id).run {
                    getTheResultByHttpCode(this.code()) {
                        _beerDetail.postValue(this.body()?.last())
                    }
                }
            } catch (e: Exception) {
                getError(R.string.unknown_error)
            }
            _beerDetailLoading.postValue(false)
        }
    }

    private fun getError(msgString: Int) {
        _beerStringError.postValue(msgString)
        _beerListError.postValue(true)
        _beerListLoading.postValue(false)
    }

    private fun getTheResultByHttpCode(code: Int, resultOk: () -> Any) {
        when (code) {
            REQUEST_OK -> {
                resultOk.invoke()
            }
            UNAUTHORIZED, FORBIDDEN -> {
                getError(R.string.UNAUTHORIZED_FORBIDDEN)
            }
            NOT_FOUND -> {
                getError(R.string.NOT_FOUND)
            }
            TIMEOUT -> {
                getError(R.string.TIMEOUT)
            }
            INTERNAL_SERVER_ERROR -> {
                getError(R.string.INTERNAL_SERVER_ERROR)
            }
        }
    }

    // TODO: Make data stream in MVVM, for the time being is Fragment

    companion object {
        const val REQUEST_OK: Int = 200
        const val UNAUTHORIZED: Int = 401
        const val FORBIDDEN: Int = 403
        const val NOT_FOUND: Int = 404
        const val TIMEOUT: Int = 408
        const val INTERNAL_SERVER_ERROR: Int = 500
    }

}