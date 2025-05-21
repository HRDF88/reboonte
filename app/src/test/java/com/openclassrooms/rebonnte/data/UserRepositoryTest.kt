package com.openclassrooms.rebonnte.data

import android.app.Activity
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.openclassrooms.rebonnte.data.repository.UserRepository
import com.openclassrooms.rebonnte.data.webService.serviceInterface.UserApi
import com.openclassrooms.rebonnte.domain.model.User
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class UserRepositoryTest {

    private lateinit var userApi: UserApi
    private lateinit var repository: UserRepository
    private val testUser = User(id = "1",name = "Jocelyn Testing", email = "jocelyn.testing@gmail.com")

    @Before
    fun setUp() {
        userApi = mockk()
        repository = UserRepository(userApi)
    }

    @Test
    fun `onSignInResult completes with user when result is OK`() {
        val result: FirebaseAuthUIAuthenticationResult = mockk {
            every { resultCode } returns Activity.RESULT_OK
        }

        every { userApi.getCurrentUser() } returns testUser

        val deferred = CompletableDeferred<User?>()
        repository.javaClass.getDeclaredField("signInDeferred").apply {
            isAccessible = true
            set(repository, deferred)
        }

        repository.onSignInResult(result)

        assertEquals(testUser, deferred.getCompleted())
    }

    @Test
    fun `onSignInResult completes with null when result is not OK`() {
        val result: FirebaseAuthUIAuthenticationResult = mockk {
            every { resultCode } returns Activity.RESULT_CANCELED
        }

        val deferred = CompletableDeferred<User?>()
        repository.javaClass.getDeclaredField("signInDeferred").apply {
            isAccessible = true
            set(repository, deferred)
        }

        repository.onSignInResult(result)

        assertNull(deferred.getCompleted())
    }


    @Test(expected = IllegalStateException::class)
    fun `signIn throws if already in progress`() = runTest {
        val launcher: ActivityResultLauncher<Intent> = mockk()

        val inProgressDeferred = CompletableDeferred<User?>()
        repository.javaClass.getDeclaredField("signInDeferred").apply {
            isAccessible = true
            set(repository, inProgressDeferred)
        }

        repository.signIn(launcher)
    }

    @Test
    fun `signOut delegates to userApi`() {
        every { userApi.signOut() } just Runs

        repository.signOut()

        verify { userApi.signOut() }
    }

    @Test
    fun `getCurrentUser returns correct user`() {
        every { userApi.getCurrentUser() } returns testUser

        val user = repository.getCurrentUser()

        assertEquals(testUser, user)
    }

    @Test
    fun `isUserLoggedIn returns expected value`() {
        every { userApi.isUserLoggedIn() } returns true

        val loggedIn = repository.isUserLoggedIn()

        assertTrue(loggedIn)
    }

    @Test
    fun `loadUser returns flow with expected user`() = runTest {
        every { userApi.loadUser() } returns flowOf(testUser)

        val result = repository.loadUser().first()

        assertEquals(testUser, result)
    }
}
