package com.openclassrooms.rebonnte.domain.usecase

import app.cash.turbine.test
import com.openclassrooms.rebonnte.data.repositoryInterface.UserRepositoryInterface
import com.openclassrooms.rebonnte.domain.model.User
import com.openclassrooms.rebonnte.domain.useCases.user.useCase.LoadUserUseCase
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Test
import kotlin.test.assertEquals

class LoadUserUseCaseTest {

    private val mockRepository = mockk<UserRepositoryInterface>()
    private val useCase = LoadUserUseCase(mockRepository)

    @Test
    fun `invoke emits user from repository flow`() = runBlocking {
        val fakeUser = User(id = "1", name = "Jocelyn Testing", email = "jocelyn.testing@gmail.com")
        every { mockRepository.loadUser() } returns flowOf(fakeUser)

        useCase().test {
            assertEquals(fakeUser, awaitItem())
            awaitComplete()
        }
    }
}

