package com.openclassrooms.rebonnte.domain.usecase

import com.openclassrooms.rebonnte.data.repositoryInterface.UserRepositoryInterface
import com.openclassrooms.rebonnte.domain.useCases.user.useCase.IsUserLoggedInUseCase
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class IsUserLoggedInUseCaseTest {

    private val mockRepository = mockk<UserRepositoryInterface>()
    private val useCase = IsUserLoggedInUseCase(mockRepository)

    @Test
    fun `invoke returns true when user is logged in`() {
        every { mockRepository.isUserLoggedIn() } returns true

        val result = useCase.invoke()

        assertTrue(result)
    }

    @Test
    fun `invoke returns false when user is not logged in`() {
        every { mockRepository.isUserLoggedIn() } returns false

        val result = useCase.invoke()

        assertFalse(result)
    }
}
