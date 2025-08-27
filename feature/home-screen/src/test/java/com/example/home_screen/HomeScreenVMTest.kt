package com.example.home_screen

import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListUpdateCallback
import com.example.data.domain.HomeScreenRepo
import com.example.home_screen.screen.HomeScreenIntent
import com.example.home_screen.screen.HomeScreenVM
import com.example.network.home_screen.models.user_playlists_response.Collection
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeScreenVMTest {

    private val repo: HomeScreenRepo = mockk(relaxed = true)

    private object PlaylistDiff : DiffUtil.ItemCallback<Collection>() {
        override fun areItemsTheSame(oldItem: Collection, newItem: Collection) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Collection, newItem: Collection) = oldItem == newItem
    }

    @Test
    fun `fetch access token update state`() {
        val testDispatcher = StandardTestDispatcher()
        val vm = HomeScreenVM(repo, testDispatcher)

        assertNull(vm.homeScreenState.value.accessToken)

        val token = "token"
        vm.sendIntent(HomeScreenIntent.FetchAccessToken(token))

        assertEquals(token, vm.homeScreenState.value.accessToken)
    }

    @Test
    fun `playlist returns correct lazy paging items`() = runTest {
        val token = "token"
        val playlists = listOf(
            Collection(id = 0),
            Collection(id = 1)
        )

        coEvery { repo.getPlaylists(token) } returns flowOf(PagingData.from(playlists))

        val testDispatcher = StandardTestDispatcher(testScheduler)

        val vm = HomeScreenVM(repo, testDispatcher)

        val differ = AsyncPagingDataDiffer(
            diffCallback = PlaylistDiff,
            updateCallback = NoopListCallback,
            workerDispatcher = UnconfinedTestDispatcher(testScheduler)
        )

        val job = launch {
            vm.playlists.collect { pagingData ->
                differ.submitData(pagingData)
            }
        }

        vm.sendIntent(HomeScreenIntent.FetchAccessToken(token))

        advanceUntilIdle()

        coVerify(exactly = 1) { repo.getPlaylists(token) }
        assertEquals(playlists.size, differ.itemCount)

        job.cancel()
    }
}

object NoopListCallback: ListUpdateCallback {
    override fun onInserted(position: Int, count: Int) {}
    override fun onRemoved(position: Int, count: Int) {}
    override fun onMoved(fromPosition: Int, toPosition: Int) {}
    override fun onChanged(position: Int, count: Int, payload: Any?) {}
}