package com.example.soundrush

import com.example.data.domain.AppStartingRepo
import com.example.data.utils.OnboardingState
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AppStartingVMTest {

    private val dispatcher = StandardTestDispatcher()
    private lateinit var vm: AppStartingVM
    private lateinit var repo: AppStartingRepo

    @Before
    fun setUp() {
        repo = mockk(relaxed = true)
        Dispatchers.setMain(dispatcher)
        vm = AppStartingVM(repo)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `onboarding state is Loading by default`() {
        assertEquals(OnboardingState.Loading, vm.onboardingState.value)
    }
}