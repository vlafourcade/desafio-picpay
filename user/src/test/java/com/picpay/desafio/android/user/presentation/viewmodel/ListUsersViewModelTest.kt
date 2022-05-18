package com.picpay.desafio.android.user.presentation.viewmodel

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.picpay.desafio.android.core.processing.model.Resource
import com.picpay.desafio.android.user.domain.model.User
import com.picpay.desafio.android.user.domain.usecase.GetUsersUseCase
import com.picpay.desafio.android.user.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.anyBoolean
import org.mockito.MockitoAnnotations
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
@Config(
    sdk = [
        Build.VERSION_CODES.JELLY_BEAN,
        Build.VERSION_CODES.KITKAT,
        Build.VERSION_CODES.LOLLIPOP,
        Build.VERSION_CODES.P]
)
class ListUsersViewModelTest {
    @get:Rule
    var instantTaskRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var useCase: GetUsersUseCase

    private val coroutineDispatcher = TestCoroutineDispatcher()

    private lateinit var viewModel: ListUsersViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this@ListUsersViewModelTest)

        viewModel = ListUsersViewModelImpl(useCase, coroutineDispatcher)
    }

    @Test
    fun testSimulationUseCaseLoadingState() = coroutineDispatcher.runBlockingTest {
        `when`(useCase.invoke(anyBoolean())).thenReturn(flow { emit(Resource.Loading()) })

        viewModel.fetchData()

        Assert.assertTrue(viewModel.isLoading.getOrAwaitValue())
    }

    @Test
    fun testSimulationUseCaseSuccessState() = coroutineDispatcher.runBlockingTest {
        val expectedValue = listOf(
            User(id = 1, name = "First User", username = "@first_user", img = "img_url")
        )

        `when`(useCase(anyBoolean())).thenReturn(flow { emit(Resource.Success(expectedValue)) })

        viewModel.fetchData()

        Assert.assertFalse(viewModel.isLoading.getOrAwaitValue())
        Assert.assertEquals(
            expectedValue,
            viewModel.data.getOrAwaitValue().getContentIfNotHandled()
        )
    }

    @Test
    fun testSimulationUseCaseErrorState() = coroutineDispatcher.runBlockingTest {
        val expectedError = Throwable("Some error message")

        `when`(useCase()).thenReturn(flow { emit(Resource.Error(expectedError, null)) })

        viewModel.fetchData()

        Assert.assertFalse(viewModel.isLoading.getOrAwaitValue())
        Assert.assertEquals(
            expectedError,
            viewModel.error.getOrAwaitValue().getContentIfNotHandled()
        )
    }
}