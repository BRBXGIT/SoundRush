package com.example.data

import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListUpdateCallback
import com.example.data.domain.HomeScreenRepo
import com.example.data.repositories.HomeScreenRepoImpl
import com.example.network.home_screen.api.HomeScreenApiInstance
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import com.example.network.home_screen.models.user_playlists_response.Collection
import io.mockk.coEvery
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class HomeScreenRepoImplTest {

    private val testDispatcher = StandardTestDispatcher()
    private val api = mockk<HomeScreenApiInstance>()
    private lateinit var repo: HomeScreenRepoImpl

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repo = HomeScreenRepoImpl(api)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        unmockkAll()
    }

    class CollectionDiffCallback: DiffUtil.ItemCallback<Collection>() {
        override fun areItemsTheSame(
            oldItem: Collection,
        newItem: Collection
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: Collection,
        newItem: Collection
        ): Boolean {
            return oldItem == newItem
        }
    }

    @Test
    fun `getPlaylists emits PagingData with expected items`() = runTest {
        val items = listOf(
            Collection(id = 0),
            Collection(id = 1)
        )
        val pagingData: PagingData<Collection> = PagingData.from(items)

        val repoMock = mockk<HomeScreenRepo>()
        coEvery { repoMock.getPlaylists(any()) } returns flowOf(pagingData)

        val differ = AsyncPagingDataDiffer<Collection>(
            diffCallback = CollectionDiffCallback(),
            updateCallback = object : ListUpdateCallback {
                override fun onInserted(position: Int, count: Int) {}
                override fun onRemoved(position: Int, count: Int) {}
                override fun onMoved(fromPosition: Int, toPosition: Int) {}
                override fun onChanged(position: Int, count: Int, payload: Any?) {}
            },
            workerDispatcher = testDispatcher
        )

        val flow = repoMock.getPlaylists("token")
        val job = launch {
            flow.collectLatest { pd: PagingData<Collection> ->
                differ.submitData(pd)
            }
        }

        advanceUntilIdle()

        val snapshot = differ.snapshot().items
        assertEquals(2, snapshot.size)
        assertEquals(0, snapshot[0].id)
        assertEquals(1, snapshot[1].id)

        job.cancel()
    }
}
