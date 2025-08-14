package com.example.network

import com.example.network.common.NetworkErrors
import com.example.network.common.NetworkRequest
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Assert
import org.junit.Test
import retrofit2.Response
import java.net.UnknownHostException

class NetworkRequestTest {

    private data class D(val v: String = "ok")

    @Test
    fun `exec returns success when response is successful with body`() = runTest {
        val body = D("hello")
        val result = NetworkRequest.exec<D> {
            Response.success(body)
        }

        Assert.assertEquals(NetworkErrors.SUCCESS, result.error)
        Assert.assertEquals(body, result.response)
    }

    @Test
    fun `exec returns success with null body when response is successful but body is null`() =
        runTest {
            val result = NetworkRequest.exec<D> {
                Response.success<D>(null)
            }

            Assert.assertEquals(NetworkErrors.SUCCESS, result.error)
            Assert.assertNull(result.response)
        }

    @Test
    fun `exec returns NetworkException when code is not 200`() = runTest {
        val errorCode = 401
        val networkError = NetworkErrors.UNAUTHORIZED

        val body = ResponseBody.create(MediaType.get("application/json"), "")

        val result = NetworkRequest.exec<D> {
            Response.error<D>(errorCode, body)
        }

        Assert.assertEquals(networkError, result.error)
        Assert.assertNull(result.response)
    }

    @Test
    fun `exec returns Internet exception when there is no connection`() = runTest {
        val result = NetworkRequest.exec<D> {
            throw UnknownHostException()
        }

        Assert.assertNull(result.response)
        Assert.assertEquals(NetworkErrors.INTERNET, result.error)
    }
}