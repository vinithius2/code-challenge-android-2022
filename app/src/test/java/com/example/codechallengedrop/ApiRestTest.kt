package com.example.codechallengedrop

//import org.mockito.Mockito
//import org.mockito.MockitoAnnotations
import androidx.lifecycle.Observer
import com.example.codechallengedrop.data.repository.BeerRepository
import com.example.codechallengedrop.data.repository.BeerRepositoryData
import com.example.codechallengedrop.data.response.Beer
import com.example.codechallengedrop.ui.BeerViewModel
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class ApiRestTest {

    @Mock
    private lateinit var beer01: Beer

    @Mock
    private lateinit var beer02: Beer

    @Mock
    private lateinit var beer03: Beer

    @Mock
    private lateinit var beerRepository: BeerRepository

    @Mock
    private lateinit var beerRepositoryData: com.example.codechallengedrop.data.repository.BeerRepositoryData
    private lateinit var viewModel: BeerViewModel

    private lateinit var beerList: List<Beer>
    private lateinit var beerDetail: List<Beer>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        beerList = listOf(beer01, beer02, beer03)
        beerDetail = listOf(beer01)
    }

    @Test
    fun `when endpoint list is successful`() = runBlockingTest {
        beerRepositoryData = BeerRepositoryData(beerRepository)
        viewModel = BeerViewModel(beerRepositoryData)
        val mockObserver = Mock(Observer::class.java)
        viewModel.beerList.observeForever(mockObserver)
        val dataList = beerRepositoryData.beerList(1)
        assert(dataList.isNotEmpty())
    }

    inner class MockBeerRepositoryData() : BeerRepository {
        override suspend fun getBeerList(number: Int): List<Beer> {
            return beerList
        }

        override suspend fun getBeerDetail(id: Int): List<Beer> {
            return beerDetail
        }
    }

}

//class BeerRepositoryData() : BeerRepository {
//    override suspend fun getBeerList(number: Int): List<Beer> {
//        return beerList
//    }
//
//    override suspend fun getBeerDetail(id: Int): List<Beer> {
//        return beerDetail
//    }
//}
