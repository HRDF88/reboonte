package com.openclassrooms.rebonnte.domain.usecase

import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.openclassrooms.rebonnte.data.repositoryInterface.UserRepositoryInterface
import com.openclassrooms.rebonnte.domain.useCases.user.useCase.OnSignInResultUseCase
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test

class OnSignInResultUseCaseTest {

    private val mockRepository = mockk<UserRepositoryInterface>(relaxed = true)
    private val useCase = OnSignInResultUseCase(mockRepository)

    @Test
    fun `invoke should call repository onSignInResult with given result`() {
        val fakeResult = mockk<FirebaseAuthUIAuthenticationResult>()

        useCase.invoke(fakeResult)

        verify { mockRepository.onSignInResult(fakeResult) }
    }
}
