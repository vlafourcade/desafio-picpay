package com.picpay.desafio.android.user.presentation.ui.fragment

import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jakewharton.espresso.OkHttp3IdlingResource
import com.picpay.desafio.android.core.networking.di.BaseUrlModule
import com.picpay.desafio.android.presentation.ui.activity.HiltTestActivity
import com.picpay.desafio.android.user.R
import com.picpay.desafio.android.user.data.MockServerDispatcher
import com.picpay.desafio.android.user.data.local.UserDatabase
import com.picpay.desafio.android.user.data.remote.UsersApi
import com.picpay.desafio.android.user.di.ApiModule
import com.picpay.desafio.android.user.presentation.adapter.UserAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import dagger.hilt.components.SingletonComponent
import dagger.hilt.internal.Preconditions
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Named

@ExperimentalCoroutinesApi
@UninstallModules(BaseUrlModule::class, ApiModule::class)
@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class UserListFragmentTest {
    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var database: UserDatabase

    private lateinit var mockWebServer: MockWebServer

    @Inject
    lateinit var okHttp: OkHttpClient

    @Module
    @InstallIn(SingletonComponent::class)
    class FakeBaseUrlModule {
        @Provides
        @Named("baseUrl")
        fun provideBaseUrl(): String = "http://localhost:8080/"
    }

    @Module
    @InstallIn(ViewModelComponent::class)
    internal class FakeApiModule {
        @Provides
        fun provideApi(retrofit: Retrofit): UsersApi = retrofit.create(UsersApi::class.java)
    }

    @Before
    fun setUp() {
        hiltRule.inject()

        database.clearAllTables()

        mockWebServer = MockWebServer()
        mockWebServer.dispatcher = MockServerDispatcher().RequestDispatcher()
        mockWebServer.start(8080)

        IdlingRegistry.getInstance().register(OkHttp3IdlingResource.create("okhttp", okHttp))
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()

        database.clearAllTables()
    }

    @Test
    fun verifyListItemsTest() {
        launchFragmentInHiltContainer<UserListFragment>()

        onView(withId(R.id.rvUsers)).check(matches(isDisplayed()))

        onView(withId(R.id.rvUsers))
            .perform(RecyclerViewActions.scrollToPosition<UserAdapter.ViewHolder>(0))
            .check(matches(hasDescendant(withText("Sandrine Spinka"))))

        onView(withId(R.id.rvUsers))
            .perform(RecyclerViewActions.scrollToPosition<UserAdapter.ViewHolder>(1))
            .check(matches(hasDescendant(withText("Carli Carroll"))))
    }
}

inline fun <reified T : Fragment> launchFragmentInHiltContainer(
    fragmentArgs: Bundle? = null,
    fragmentFactory: FragmentFactory? = null,
    crossinline action: Fragment.() -> Unit = {}
) {
    val startActivityIntent = Intent.makeMainActivity(
        ComponentName(
            ApplicationProvider.getApplicationContext(),
            HiltTestActivity::class.java
        )
    )

    ActivityScenario.launch<HiltTestActivity>(startActivityIntent).onActivity { activity ->
        fragmentFactory?.let {
            activity.supportFragmentManager.fragmentFactory = it
        }
        val fragment: Fragment = activity.supportFragmentManager.fragmentFactory.instantiate(
            Preconditions.checkNotNull(T::class.java.classLoader),
            T::class.java.name
        )

        fragment.arguments = fragmentArgs
        activity.supportFragmentManager
            .beginTransaction()
            .add(android.R.id.content, fragment, "")
            .commitNow()

        fragment.action()
    }
}