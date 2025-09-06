package com.example.home_screen

import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListUpdateCallback
import app.cash.turbine.test
import com.example.data.domain.HomeScreenRepo
import com.example.design_system.snackbars.SnackbarController
import com.example.home_screen.screen.HomeScreenIntent
import com.example.home_screen.screen.HomeScreenState
import com.example.home_screen.screen.HomeScreenVM
import com.example.network.common.NetworkErrors
import com.example.network.common.NetworkResponse
import com.example.network.home_screen.models.user_playlists_response.Collection
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeScreenVMTest {

    private val repo: HomeScreenRepo = mockk(relaxed = true)
    private val dispatcher = StandardTestDispatcher()

    private lateinit var vm: HomeScreenVM

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        vm = HomeScreenVM(repo, dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `screen state initialize with basic values`() {
        val state = vm.homeScreenState.value

        assertEquals(HomeScreenState(), state)
    }

    @Test
    fun `intents update state correctly`() = runTest {
        val token = "123"
        val description = "description"
        val name = "name"

        vm.homeScreenState.test {
            // initial state
            val initial = awaitItem()
            assertNull(initial.accessToken)
            assertFalse(initial.isCreatePlaylistBSVisible)
            assertEquals("", initial.playlistDescription)
            assertEquals("", initial.playlistName)
            assertFalse(initial.didVibrate)
            assertEquals(0, initial.refreshTrigger)

            // FetchAccessToken
            vm.sendIntent(HomeScreenIntent.FetchAccessToken(token))
            val afterToken = awaitItem()
            assertEquals(token, afterToken.accessToken)

            // ChangeCreatePlaylistBSVisibility
            vm.sendIntent(HomeScreenIntent.ChangeCreatePlaylistBSVisibility)
            val afterVisibility = awaitItem()
            assertTrue(afterVisibility.isCreatePlaylistBSVisible)

            // ChangePlaylistDescription
            vm.sendIntent(HomeScreenIntent.ChangePlaylistDescription(description))
            val afterDescription = awaitItem()
            assertEquals(description, afterDescription.playlistDescription)

            // ChangePlaylistName
            vm.sendIntent(HomeScreenIntent.ChangePlaylistName(name))
            val afterName = awaitItem()
            assertEquals(name, afterName.playlistName)

            // ChangeDidVibrate
            vm.sendIntent(HomeScreenIntent.ChangeDidVibrate(true))
            val afterVibrate = awaitItem()
            assertTrue(afterVibrate.didVibrate)

            // Change refresh trigger
            vm.sendIntent(HomeScreenIntent.RefreshPlaylists)
            val refreshTrigger = awaitItem()
            assertEquals(1, refreshTrigger.refreshTrigger)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `create playlist refresh playlists after success call`() = runTest {
        vm.homeScreenState.test {
            val initial = awaitItem()
            assertFalse(initial.isCreatePlaylistBSVisible)
            assertEquals(0, initial.refreshTrigger)
            vm.sendIntent(HomeScreenIntent.ChangeCreatePlaylistBSVisibility)
            val after = awaitItem()
            assertTrue(after.isCreatePlaylistBSVisible)

            val successResponse = NetworkResponse(
                response = Unit,
                label = "Success",
                error = NetworkErrors.SUCCESS
            )
            coEvery { repo.createPlaylist(
                any(),
                any(),
                any()
            ) } returns successResponse

            vm.sendIntent(HomeScreenIntent.CreatePlaylist)
            val afterCreate = awaitItem()
            assertFalse(afterCreate.isCreatePlaylistBSVisible)
            assertEquals(1, afterCreate.refreshTrigger)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `create playlist calls snackbar method after error`() = runTest {
        SnackbarController.events.test {
            val errorResponse = NetworkResponse(
                response = Unit,
                label = "Seems you are unauthorized",
                error = NetworkErrors.UNAUTHORIZED
            )
            coEvery { repo.createPlaylist(
                any(),
                any(),
                any()
            ) } returns errorResponse

            vm.sendIntent(HomeScreenIntent.CreatePlaylist)

            val after = awaitItem()
            assertNotNull(after.action)
            assertEquals(after.message, "Seems you are unauthorized")
            assertEquals(after.action?.name, "Retry")

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `delete playlists refresh playlists after success call and change delete mode on start`() = runTest {
        vm.homeScreenState.test {
            val initial = awaitItem()
            assertEquals(0, initial.refreshTrigger)
            assertFalse(initial.isInDeleteMode)

            val urn = "123"
            val name = "playlist"
            vm.sendIntent(HomeScreenIntent.AddUrnToDeleteList(urn, name))

            val successResult = NetworkResponse(
                response = Unit,
                error = NetworkErrors.SUCCESS,
                label = "Success"
            )
            coEvery { repo.deletePlaylist(any(), any()) } returns successResult

            vm.sendIntent(HomeScreenIntent.DeletePlaylists)
            advanceUntilIdle()

            skipItems(1)
            val after = awaitItem()
            assertFalse(after.isInDeleteMode)
            assertEquals(1, after.refreshTrigger)
        }
    }

    @Test
    fun `delete playlists calls snackbar method after error`() = runTest {
        SnackbarController.events.test {
            val errorResponse = NetworkResponse(
                response = Unit,
                label = "Seems you are unauthorized",
                error = NetworkErrors.UNAUTHORIZED
            )

            val urn = "123"
            val name = "playlist"
            vm.sendIntent(HomeScreenIntent.AddUrnToDeleteList(urn, name))

            coEvery { repo.deletePlaylist(any(), urn) } returns errorResponse

            vm.sendIntent(HomeScreenIntent.DeletePlaylists)
            advanceUntilIdle()

            val after = awaitItem()
            assertEquals(after.message, "Problem with deleting playlist: $name")
        }
    }

    @Test
    fun `playlists emits data when accessToken and refreshTrigger change`() = runTest {
        // Data mock
        val collections = listOf(
            Collection(id = 1, title = "First"),
            Collection(id = 2, title = "Second")
        )

        // Flow mock
        val pagingData = PagingData.from(collections)
        val flow = flowOf(pagingData)

        coEvery { repo.getPlaylists(any()) } returns flow

        // Check playlists
        vm.playlists.test {
            vm.sendIntent(HomeScreenIntent.FetchAccessToken("token_123"))
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