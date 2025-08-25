package com.example.data

import com.example.data.domain.HomeScreenRepo
import com.example.data.repositories.HomeScreenRepoImpl
import com.example.network.home_screen.api.HomeScreenApiInstance
import io.mockk.mockk
import org.junit.Before

class HomeScreenRepoTest {

    private lateinit var repo: HomeScreenRepo
    private val api: HomeScreenApiInstance = mockk()

    @Before
    fun setUp() {
        repo = HomeScreenRepoImpl(api)
    }


}