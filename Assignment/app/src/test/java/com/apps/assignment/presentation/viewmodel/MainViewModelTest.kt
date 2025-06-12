package com.apps.assignment.presentation.viewmodel

import com.apps.assignment.common.models.FetchListModel
import com.apps.assignment.common.models.UserSummary
import com.apps.assignment.repository.IGithubRepository
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import okhttp3.ResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest {
    private lateinit var viewModel: MainViewModel
    private val githubRepository = mockk<IGithubRepository>()
    private val userSummary = UserSummary("",1,"","",null,"","","","","","","","","","","","",true,Double.MAX_VALUE)

    @Before
    fun setup(){
        Dispatchers.setMain(StandardTestDispatcher())
        mockkStatic(Dispatchers::class)
        every { Dispatchers.IO } returns Dispatchers.Main
        viewModel = MainViewModel(githubRepository)
    }

    @Test
    fun testSearchUserDataWithInvalidLength() = runTest{
        viewModel.updateTextField("te")
        viewModel.searchUserData()
        assertEquals(viewModel.errorState.value, TextErrorState.IncorrectLength)
    }
    @Test
    fun testSearchUserDataWithValidLengthAndFirstSearch() = runTest{
        coEvery { githubRepository.searchPrefix(any(), any()) } returns Response.success(FetchListModel(1, false, listOf(userSummary)))
        viewModel.updateTextField("test")
        viewModel.searchUserData(true)
        advanceUntilIdle()
        assertEquals(viewModel.errorState.first(), TextErrorState.None)
        assertTrue(viewModel.userList.first().isNotEmpty())
    }
    @Test
    fun testSearchUserDataWithValidLength() = runTest{
        coEvery { githubRepository.searchPrefix(any(), any()) } returns Response.success(FetchListModel(1, false, listOf(userSummary)))
        viewModel.updateTextField("test")
        viewModel.searchUserData()
        advanceUntilIdle()
        assertEquals(viewModel.errorState.first(), TextErrorState.None)
        assertTrue(viewModel.userList.first().isNotEmpty())
    }
    @Test
    fun testSearchUserDataWithValidLengthButNoResponse() = runTest{
        coEvery { githubRepository.searchPrefix(any(), any()) } returns Response.success(FetchListModel(1, false, listOf()))
        viewModel.updateTextField("test")
        viewModel.searchUserData()
        advanceUntilIdle()
        assertEquals(viewModel.errorState.first(), TextErrorState.NoUserFound)
        assertTrue(viewModel.userList.first().isEmpty())
    }
    @Test
    fun testSearchUserDataWithApiError() = runTest{
        coEvery { githubRepository.searchPrefix(any(), any()) } returns Response.error(401, ResponseBody.Companion.create(null, ""))
        viewModel.updateTextField("test")
        viewModel.searchUserData()
        advanceUntilIdle()
        assertTrue(viewModel.apiErrorState.first())
        assertTrue(viewModel.userList.first().isEmpty())
    }
}