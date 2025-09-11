package com.example.playlist_screen

import androidx.paging.compose.LazyPagingItems
import com.example.common.state.CommonIntent
import com.example.common.state.CommonVM
import com.example.playlist_screen.screen.handleTrackClick
import io.mockk.every
import io.mockk.mockk
import com.example.network.playlist_screen.models.Collection
import com.example.network.playlist_screen.models.User
import io.mockk.verify
import org.junit.Test

class HandleTrackClickTest {

    private val vm: CommonVM = mockk(relaxed = true)

    @Test
    fun `when clicked track exists then queue and currentTrack are sent`() {
        // given
        val track1 = collection("t1", "a1", "u1")
        val track2 = collection("t2", "a2", "u2")
        val tracks = fakeLazyPagingItems(listOf(track1, track2))

        // when
        handleTrackClick(track1, tracks, vm)

        // then
        verify { vm.sendIntent(CommonIntent.SetQueue(match { it.size == 2 })) }
        verify { vm.sendIntent(CommonIntent.SetCurrentTrack(match { it.link == track1.streamUrl })) }
    }

    @Test
    fun `when clicked track not in list then empty queue`() {
        val track1 = collection("t1", "a1", "u1")
        val tracks = fakeLazyPagingItems(listOf(track1))

        handleTrackClick(collection("not-found", "a", "u"), tracks, vm)

        verify { vm.sendIntent(CommonIntent.SetQueue(emptyList())) }
        verify(exactly = 0) { vm.sendIntent(ofType<CommonIntent.SetCurrentTrack>()) }
    }

    // --- helpers ---
    private fun collection(streamUrl: String, artworkUrl: String, username: String) =
        Collection(
            streamUrl = streamUrl,
            artworkUrl = artworkUrl,
            title = "title-$streamUrl",
            user = User(username)
        )

    private fun fakeLazyPagingItems(list: List<Collection>): LazyPagingItems<Collection> {
        val items: LazyPagingItems<Collection> = mockk()
        every { items.itemSnapshotList.items } returns list
        return items
    }
}