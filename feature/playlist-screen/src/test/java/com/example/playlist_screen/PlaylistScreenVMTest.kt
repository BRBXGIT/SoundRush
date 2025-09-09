package com.example.playlist_screen

import app.cash.turbine.test
import com.example.common.state.CommonIntent
import com.example.data.domain.PlaylistScreenRepo
import com.example.playlist_screen.screen.PlaylistScreenIntent
import com.example.playlist_screen.screen.PlaylistScreenState
import com.example.playlist_screen.screen.PlaylistScreenVM
import io.mockk.awaits
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class PlaylistScreenVMTest {

    private val repo: PlaylistScreenRepo = mockk(relaxed = true)

    private lateinit var vm: PlaylistScreenVM

    @Before
    fun setUp() {
        vm = PlaylistScreenVM(repo)
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
}