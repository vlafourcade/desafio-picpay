@file:OptIn(ExperimentalCoroutinesApi::class)

package com.picpay.desafio.android.contact.domain.usecase

import android.os.Build
import com.picpay.desafio.android.contact.domain.model.dto.ContactDto
import com.picpay.desafio.android.contact.domain.repository.ContactsRepository
import com.picpay.desafio.android.core.processing.model.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.JELLY_BEAN, Build.VERSION_CODES.KITKAT, Build.VERSION_CODES.LOLLIPOP, Build.VERSION_CODES.P])
class GetContactsUseCaseTest {
    @Mock
    private lateinit var repository: ContactsRepository

    private lateinit var useCase: GetContactsUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this@GetContactsUseCaseTest)

        useCase = GetContactsUseCaseImpl(repository)
    }

    @Test
    fun testGetContactsWithSuccess() = runBlockingTest {
        val mockedResult = listOf(
            ContactDto(1, "img_url", "First Contact", "first_contact")
        )

        `when`(repository.getContacts()).thenReturn(mockedResult)

        useCase().collect {
            when(it) {
                is Resource.Success -> {
                    Assert.assertNotNull(it.data)
                    Assert.assertEquals(1, it.data?.size)

                    with(it.data?.first()) {
                        Assert.assertEquals(1, this?.id)
                        Assert.assertEquals("img_url", this?.img)
                        Assert.assertEquals("First Contact", this?.name)
                        Assert.assertEquals("first_contact", this?.username)
                    }
                }
                is Resource.Error -> { Assert.fail() }
                else -> {}
            }
        }
    }

    @Test
    fun testGetContactsWithError() = runBlockingTest {
        `when`(
            repository.getContacts()
        ).thenThrow(Throwable::class.java)

        useCase().collect {
            when(it) {
                is Resource.Success -> {
                    Assert.fail()
                }
                is Resource.Error -> {
                    Assert.assertNotNull(it.error)
                    Assert.assertTrue(it.error is Throwable)
                }
                else -> {
                }
            }
        }
    }
}