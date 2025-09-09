package com.example.playlist_screen

import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListUpdateCallback
import app.cash.turbine.test
import com.example.data.domain.PlaylistScreenRepo
import com.example.playlist_screen.screen.PlaylistScreenIntent
import com.example.playlist_screen.screen.PlaylistScreenState
import com.example.playlist_screen.screen.PlaylistScreenVM
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import com.example.network.playlist_screen.models.Collection
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After

@OptIn(ExperimentalCoroutinesApi::class)
class PlaylistScreenVMTest {

    private val repo: PlaylistScreenRepo = mockk(relaxed = true)
    private val dispatcher = StandardTestDispatcher()

    private lateinit var vm: PlaylistScreenVM

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        vm = PlaylistScreenVM(repo)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `screen state is default on start`() {
        assertEquals(vm.playlistScreenState.value, PlaylistScreenState())
    }

    @Test
    fun `screen state updates correctly`() = runTest {
        vm.playlistScreenState.test {
            val initial = awaitItem()
            assertEquals(initial, PlaylistScreenState())

            val token = "123"
            vm.sendIntent(PlaylistScreenIntent.FetchAccessToken(token))
            val afterToken = awaitItem()
            assertEquals(token, afterToken.accessToken)

            val urn = "12345"
            vm.sendIntent(PlaylistScreenIntent.SetPlaylistUrn(urn))
            val afterUrn = awaitItem()
            assertEquals(urn, afterUrn.playlistUrn)

            vm.sendIntent(PlaylistScreenIntent.RefreshTracks)
            val afterTrigger = awaitItem()
            assertEquals(1, afterTrigger.refreshTrigger)

            vm.sendIntent(PlaylistScreenIntent.ChangeDidVibrate)
            val afterDidVibrate = awaitItem()
            assertEquals(true, afterDidVibrate.didVibrate)
        }
    }

    @Test
    fun `playlists emits data when accessToken and refreshTrigger change`() = runTest {
        // Data mock
        val collections = listOf(
            Collection(
                id = 1,
                title = "First"
            ),
            Collection(
                id = 2,
                title = "Second"
            )
        )

        // Flow mock
        val pagingData = PagingData.from(collections)
        val flow = flowOf(pagingData)

        coEvery { repo.getPlaylistTracks(any(), any()) } returns flow

        // Check playlists
        vm.tracks.test {
            vm.sendIntent(PlaylistScreenIntent.FetchAccessToken("token_123"))
            advanceUntilIdle()

            val item = awaitItem()

            val differ = AsyncPagingDataDiffer(
                diffCallback = diffCallbackStub(),
                updateCallback = noopListCallback(),
                mainDispatcher = Dispatchers.Main,
                workerDispatcher = Dispatchers.Main,
            )
            differ.submitData(item)

            advanceUntilIdle()

            assertEquals(2, differ.itemCount)
            assertEquals("First", differ.getItem(0)?.title)
            assertEquals("Second", differ.getItem(1)?.title)

            cancelAndIgnoreRemainingEvents()
        }
    }

    // Stub
    private fun diffCallbackStub() = object : DiffUtil.ItemCallback<Collection>() {
        override fun areItemsTheSame(oldItem: Collection, newItem: Collection) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Collection, newItem: Collection) =
            oldItem == newItem
    }

    // Stub
    private fun noopListCallback() = object : ListUpdateCallback {
        override fun onInserted(position: Int, count: Int) {}
        override fun onRemoved(position: Int, count: Int) {}
        override fun onMoved(fromPosition: Int, toPosition: Int) {}
        override fun onChanged(position: Int, count: Int, payload: Any?) {}
    }
}