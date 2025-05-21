package com.openclassrooms.rebonnte.domain.usecase

import com.openclassrooms.rebonnte.data.repositoryInterface.UserRepositoryInterface
import com.openclassrooms.rebonnte.domain.model.User
import com.openclassrooms.rebonnte.domain.useCases.user.useCase.GetCurrentUserUseCase
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class GetCurrentUserUseCaseTest {

    private val mockRepository = mockk<UserRepositoryInterface>()
    private val useCase = GetCurrentUserUseCase(mockRepository)

    @Test
    fun `invoke returns current user when user is authenticated`() {
        val user = User(id = "1", name = "Jocelyn Testing", email = "jocelyn.testing@gmail.com")
        every { mockRepository.getCurrentUser() } returns user

        val result = useCase.invoke()

        assertEquals(user, result)
    }

    @Test
    fun `invoke returns null when no user is authenticated`() {
        every { mockRepository.getCurrentUser() } returns null

        val result = useCase.invoke()

        assertNull(result)
    }
}
