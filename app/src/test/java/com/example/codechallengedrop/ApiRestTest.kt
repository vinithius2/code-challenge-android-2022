package com.example.codechallengedrop

import android.app.Application
import com.example.codechallengedrop.data.repository.BeerRepository
import com.example.codechallengedrop.data.repository.BeerRepositoryData
import com.example.codechallengedrop.data.response.Beer
import com.example.codechallengedrop.extension.capitalize
import com.example.codechallengedrop.ui.BeerViewModel
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response
import java.util.*

class ApiRestTest {

    @Mock
    private lateinit var beer01: Beer
    @Mock
    private lateinit var beer02: Beer
    @Mock
    private lateinit var beer03: Beer
    private var beerList: List<Beer> = listOf(beer01, beer02, beer03)

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun `when endpoint list is successful`() {
        MockRepository(beerList)



        val beerViewModel = BeerViewModel(Mockito.mock(BeerRepositoryData::class.java))
//        beerViewModel.beerList.

        val spy = Mockito.spy(beerViewModel)
        spy.getBeerList()
        Mockito.verify(spy).
//        beerViewModel.beerList.value = beerList
//        beerViewModel.beerList.
//        Assert.assertEquals(beerList, "challenge".capitalize())
    }

}

class MockRepository(private val result: List<Beer>) : BeerRepository {
    override suspend fun getBeerList(number: Int): Response<List<Beer>> {
        TODO("Not yet implemented")
    }

    override suspend fun getBeerDetail(id: Int): Response<List<Beer>> {
        TODO("Not yet implemented")
    }
}