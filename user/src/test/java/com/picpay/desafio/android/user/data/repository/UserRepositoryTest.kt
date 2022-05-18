@file:OptIn(ExperimentalCoroutinesApi::class)

package com.picpay.desafio.android.user.data.repository

import android.os.Build
import com.picpay.desafio.android.core.data.local.LocalStorage
import com.picpay.desafio.android.core.data.model.ApiError
import com.picpay.desafio.android.core.utils.logging.Logger
import com.picpay.desafio.android.user.data.local.UsersDao
import com.picpay.desafio.android.user.data.model.UserEntity
import com.picpay.desafio.android.user.data.model.UserResponse
import com.picpay.desafio.android.user.data.remote.UsersApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(
    sdk = [
        Build.VERSION_CODES.JELLY_BEAN,
        Build.VERSION_CODES.KITKAT,
        Build.VERSION_CODES.LOLLIPOP,
        Build.VERSION_CODES.P]
)
class UserRepositoryTest {
    @Mock
    private lateinit var api: UsersApi

    @Mock
    private lateinit var dao: UsersDao

    @Mock
    private lateinit var localStorage: LocalStorage

    @Mock
    private lateinit var logger: Logger

    private lateinit var repository: UsersRepositoryImpl

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this@UserRepositoryTest)

        repository = UsersRepositoryImpl(
            api = api,
            dao = dao,
            localStorage = localStorage,
            logger = logger
        )
    }

    @Test
    fun getDataFromCacheTest() = runBlockingTest {
        `when`(dao.getAll()).thenReturn(
            listOf(
                UserEntity(
                    id = 1,
                    img = "img_url",
                    name = "User Name",
                    username = "username"
                )
            )
        )

        val result = repository.getContacts(forceUpdate = false)

        Assert.assertNotNull(result)
        Assert.assertTrue(result?.isNotEmpty() ?: false)
        with(result?.first()) {
            Assert.assertEquals(1, this?.id)
            Assert.assertEquals("img_url", this?.img)
            Assert.assertEquals("User Name", this?.name)
            Assert.assertEquals("username", this?.username)
        }
    }

    @Test
    fun getDataForcingUpdateTest() = runBlockingTest {
        `when`(api.getUsers()).thenReturn(
            listOf(
                UserResponse(
                    id = 1,
                    img = "img_url",
                    name = "User Name",
                    username = "username"
                )
            )
        )
        `when`(dao.insertAll(anyList())).then { }
        `when`(localStorage.put(anyString(), anyLong())).then { }
        `when`(dao.getAll()).thenReturn(
            listOf(
                UserEntity(
                    id = 1,
                    img = "img_url",
                    name = "User Name",
                    username = "username"
                )
            )
        )

        val result = repository.getContacts(forceUpdate = true)

        Assert.assertNotNull(result)
        Assert.assertTrue(result?.isNotEmpty() ?: false)
        with(result?.first()) {
            Assert.assertEquals(1, this?.id)
            Assert.assertEquals("img_url", this?.img)
            Assert.assertEquals("User Name", this?.name)
            Assert.assertEquals("username", this?.username)
        }
    }

    @Test
    fun getDataIgnoringApiErrorTest() = runBlockingTest {
        val errorMessage = "Error message"
        val error = mock((Throwable::class.java))
        `when`(error.localizedMessage).then { errorMessage }

        `when`(logger.e(errorMessage, error)).then {  }

        `when`(api.getUsers()).thenThrow(error)
        `when`(dao.getAll()).thenReturn(
            listOf(
                UserEntity(
                    id = 1,
                    img = "img_url",
                    name = "User Name",
                    username = "username"
                )
            )
        )

        val result = repository.getContacts(forceUpdate = true, ignoreApiErrors = true)

        Assert.assertNotNull(result)
        Assert.assertTrue(result?.isNotEmpty() ?: false)
        with(result?.first()) {
            Assert.assertEquals(1, this?.id)
            Assert.assertEquals("img_url", this?.img)
            Assert.assertEquals("User Name", this?.name)
            Assert.assertEquals("username", this?.username)
        }
    }

    @Test(expected = ApiError.Unknown::class)
    fun getDataWithApiErrorTest() = runBlockingTest {
        val errorMessage = "Error message"
        val error = mock((Throwable::class.java))
        `when`(error.localizedMessage).then { errorMessage }

        `when`(logger.e(errorMessage, error)).then {  }

        `when`(api.getUsers()).thenThrow(error)

        repository.getContacts(forceUpdate = true, ignoreApiErrors = false)
    }

    @Test
    fun getLatestRefreshTimeTest() = runBlockingTest {
        `when`(localStorage.get("USER_UPDATE_TIME_STORAGE_KEY", Long::class.java)).thenReturn(1000)

        Assert.assertEquals(1000, repository.getLatestRefreshTime())

        `when`(localStorage.get("USER_UPDATE_TIME_STORAGE_KEY", Long::class.java)).thenReturn(null)

        Assert.assertEquals(0, repository.getLatestRefreshTime())
    }
}
