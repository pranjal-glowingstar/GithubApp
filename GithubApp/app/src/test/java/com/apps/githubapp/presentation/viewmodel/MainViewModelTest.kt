package com.apps.githubapp.presentation.viewmodel

import com.apps.githubapp.common.AppUtils
import com.apps.githubapp.common.DispatcherUtil
import com.apps.githubapp.common.models.FetchListModel
import com.apps.githubapp.common.models.UserSummary
import com.apps.githubapp.repository.IGithubRepository
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
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
    private val testDispatcher = StandardTestDispatcher()
    private val userSummary = UserSummary("",1,"","",null,"","","","","","","","","","","","",true,Double.MAX_VALUE)

    @Before
    fun setup(){
        Dispatchers.setMain(testDispatcher)
        mockkObject(DispatcherUtil)
        every { DispatcherUtil.getIoDispatcher() } returns testDispatcher
        viewModel = MainViewModel(githubRepository)
    }

    @Test
    fun testSearchUserDataWithInvalidLength() = runTest{
        viewModel.updateTextField("te")
        delay(AppUtils.AppConstants.SEARCH_DEBOUNCE_TIME*2)
        assertEquals(viewModel.uiState.first(), UIState.IncorrectLength)
    }
    @Test
    fun testSearchUserDataWithValidLengthAndFirstSearch() = runTest{
        coEvery { githubRepository.searchPrefix(any(), any()) } returns Response.success(FetchListModel(1, false, listOf(userSummary)))
        viewModel.updateTextField("test")
        viewModel.searchUserData()
        advanceUntilIdle()
        assertEquals(viewModel.uiState.first(), UIState.None)
        assertTrue(viewModel.userList.first().isNotEmpty())
    }
    @Test
    fun testSearchUserDataWithValidLength() = runTest{
        coEvery { githubRepository.searchPrefix(any(), any()) } returns Response.success(FetchListModel(1, false, listOf(userSummary)))
        viewModel.updateTextField("test")
        viewModel.searchUserData()
        advanceUntilIdle()
        assertEquals(viewModel.uiState.first(), UIState.None)
        assertTrue(viewModel.userList.first().isNotEmpty())
    }
    @Test
    fun testSearchUserDataWithValidLengthButNoResponse() = runTest{
        coEvery { githubRepository.searchPrefix(any(), any()) } returns Response.success(FetchListModel(1, false, listOf()))
        viewModel.updateTextField("test")
        viewModel.searchUserData()
        advanceUntilIdle()
        assertEquals(viewModel.uiState.first(), UIState.NoUserFound)
        assertTrue(viewModel.userList.first().isEmpty())
    }
    @Test
    fun testSearchUserDataWithApiError() = runTest{
        coEvery { githubRepository.searchPrefix(any(), any()) } returns Response.error(401, ResponseBody.Companion.create(null, ""))
        viewModel.updateTextField("test")
        viewModel.searchUserData()
        advanceUntilIdle()
        assertTrue(viewModel.userList.first().isEmpty())
    }
}