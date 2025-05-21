package com.openclassrooms.rebonnte.domain.usecase

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import com.openclassrooms.rebonnte.data.repositoryInterface.UserRepositoryInterface
import com.openclassrooms.rebonnte.domain.model.User
import com.openclassrooms.rebonnte.domain.useCases.user.useCase.SignInUserUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class SignInUserUseCaseTest {

    private val mockRepository = mockk<UserRepositoryInterface>()
    private val useCase = SignInUserUseCase(mockRepository)

    @Test
    fun `invoke should call repository signIn and return user`() = runBlocking {
        val mockLauncher = mockk<ActivityResultLauncher<Intent>>()
        val fakeUser = User(id = "1", name = "Jocelyn Testing", email = "jocelyn.testing@gmail.com")

        coEvery { mockRepository.signIn(mockLauncher) } returns fakeUser

        val result = useCase(mockLauncher)

        assertEquals(fakeUser, result)
        coVerify { mockRepository.signIn(mockLauncher) }
    }
}
