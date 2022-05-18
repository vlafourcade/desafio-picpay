package com.picpay.desafio.android.user.data

import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
import java.util.concurrent.TimeUnit

class MockServerDispatcher {
    internal inner class RequestDispatcher : Dispatcher() {
        override fun dispatch(request: RecordedRequest): MockResponse {
            return when (request.path) {
                "/users" -> MockResponse().setResponseCode(200).setBodyDelay(3, TimeUnit.MINUTES)
                    .setBody(("[{\"id\":1001,\"name\":\"Eduardo Santos\",\"img\":\"https://randomuser.me/api/portraits/men/9.jpg\",\"username\":\"@eduardo.santos\"}]"))
                else -> MockResponse().setResponseCode(400)
            }
        }
    }
}