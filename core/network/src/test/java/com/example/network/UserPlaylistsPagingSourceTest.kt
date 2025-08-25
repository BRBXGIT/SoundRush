package com.example.network

import androidx.paging.LoadState
import io.mockk.*
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import androidx.paging.PagingSource
import com.example.network.common.NetworkErrors
import com.example.network.common.NetworkException
import com.example.network.common.NetworkRequest
import com.example.network.common.NetworkResponse
import com.example.network.home_screen.api.HomeScreenApiInstance
import com.example.network.home_screen.models.user_playlists_response.Collection
import com.example.network.home_screen.models.user_playlists_response.UserPlaylistsResponse
import com.example.network.home_screen.paging.UserPlaylistsPagingSource
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import retrofit2.Response

class UserPlaylistsPagingSourceTest {

    private val api = mockk<HomeScreenApiInstance>()

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        mockkObject(NetworkRequest)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `load returns Page on success`() = runTest {
        // Fake response
        val fakeCollection = listOf(Collection(id = 0), Collection(id = 1))
        val userResp = UserPlaylistsResponse(collection = fakeCollection, nextHref = "")
        val response = NetworkResponse(
            error = NetworkErrors.SUCCESS,
            response = userResp,
            label = ""
        )

        coEvery { NetworkRequest.exec(any<suspend () -> Response<UserPlaylistsResponse>>()) } returns response

        val source = UserPlaylistsPagingSource(api, accessToken = "token")

        val params = PagingSource.LoadParams.Refresh<String>(key = null, loadSize = 20, placeholdersEnabled = false)
        val result = source.load(params)

        assertTrue(result is PagingSource.LoadResult.Page)
        result as PagingSource.LoadResult.Page
        assertEquals(fakeCollection, result.data)
        assertEquals(null, result.prevKey)
        assertEquals(null, result.nextKey)
    }

    @Test
    fun `load returns Error on network failure`() = runTest {
        val label = "fail"
        val response = NetworkResponse(
            error = NetworkErrors.UNAUTHORIZED,
            response = UserPlaylistsResponse(),
            label = label
        )

        coEvery { NetworkRequest.exec(any<suspend () -> Response<UserPlaylistsResponse>>()) } returns response

        val source = UserPlaylistsPagingSource(api, accessToken = "token")
        val params = PagingSource.LoadParams.Refresh<String>(key = null, loadSize = 20, placeholdersEnabled = false)
        val result = source.load(params)

        assertTrue(result is PagingSource.LoadResult.Error)
        result as PagingSource.LoadResult.Error
        assertEquals(NetworkErrors.UNAUTHORIZED, (result.throwable as NetworkException).error)
        assertEquals(label, (result.throwable as NetworkException).label)
    }
}
