package com.example.home_screen

import app.cash.turbine.test
import com.example.data.domain.HomeScreenRepo
import com.example.design_system.snackbars.SnackbarController
import com.example.home_screen.screen.HomeScreenIntent
import com.example.home_screen.screen.HomeScreenState
import com.example.home_screen.screen.HomeScreenVM
import com.example.network.common.NetworkError
import com.example.network.common.NetworkErrors
import com.example.network.common.NetworkResponse
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
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

    private fun <T> generateNetworkResponse(
        response: T,
        label: String,
        error: NetworkError
    ): NetworkResponse<T> {
        return NetworkResponse(
            response = response,
            label = label,
            error = error
        )
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

            val successResponse = generateNetworkResponse(Unit, "Success", NetworkErrors.SUCCESS)
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
            val errorResponse = generateNetworkResponse(
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
}