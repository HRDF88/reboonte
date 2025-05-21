package com.openclassrooms.rebonnte.domain.usecase

import com.openclassrooms.rebonnte.data.repositoryInterface.UserRepositoryInterface
import com.openclassrooms.rebonnte.domain.useCases.user.useCase.SignOutUserUseCase
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test

class SignOutUserUseCaseTest {

    private val mockRepository = mockk<UserRepositoryInterface>(relaxed = true)
    private val useCase = SignOutUserUseCase(mockRepository)

    @Test
    fun `invoke should call repository signOut`() {
        useCase()
        verify { mockRepository.signOut() }
    }
}
