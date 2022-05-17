@file:OptIn(ExperimentalCoroutinesApi::class)

package com.picpay.desafio.android.user.domain.usecase

import android.os.Build
import com.picpay.desafio.android.core.data.model.ApiError
import com.picpay.desafio.android.core.processing.model.Resource
import com.picpay.desafio.android.user.domain.model.dto.UserDto
import com.picpay.desafio.android.user.domain.repository.UsersRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.anyBoolean
import org.mockito.MockitoAnnotations
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.JELLY_BEAN, Build.VERSION_CODES.KITKAT, Build.VERSION_CODES.LOLLIPOP, Build.VERSION_CODES.P])
class GetUsersUseCaseTest {
    @Mock
    private lateinit var repository: UsersRepository

    private lateinit var useCase: GetUsersUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this@GetUsersUseCaseTest)

        runBlockingTest {
            `when`(repository.getLatestRefreshTime()).thenReturn(10000000000)
        }

        useCase = GetUsersUseCaseImpl(repository)
    }

    @Test
    fun testGetUsersWithSuccess() = runBlockingTest {
        val mockedResult = listOf(
            UserDto(1, "img_url", "First User", "first_user")
        )

        `when`(
            repository.getContacts(
                forceUpdate = anyBoolean(),
                ignoreApiErrors = anyBoolean()
            )
        ).thenReturn(
            mockedResult
        )

        useCase().collect {
            when (it) {
                is Resource.Success -> {
                    Assert.assertNotNull(it.data)
                    Assert.assertEquals(1, it.data?.size)

                    with(it.data?.first()) {
                        Assert.assertEquals(1, this?.id)
                        Assert.assertEquals("img_url", this?.img)
                        Assert.assertEquals("First User", this?.name)
                        Assert.assertEquals("@first_user", this?.username)
                    }
                }
                is Resource.Error -> {
                    Assert.fail()
                }
                else -> {}
            }
        }
    }

    @Test
    fun testGetUsersWithError() = runBlockingTest {
        `when`(
            repository.getContacts(forceUpdate = anyBoolean(), ignoreApiErrors = anyBoolean())
        ).thenThrow(ApiError.InternetUnavailable::class.java)

        useCase().collect {
            when (it) {
                is Resource.Success -> {
                    Assert.fail()
                }
                is Resource.Error -> {
                    Assert.assertNotNull(it.error)
                    Assert.assertTrue(it.error is ApiError.InternetUnavailable)
                }
                else -> {
                }
            }
        }
    }
}