package com.example.memoryapp

import androidx.core.os.bundleOf
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.memoryapp.ui.activities.MainActivity
import com.example.memoryapp.ui.fragment.instruction.InstructionFragment
import com.example.memoryapp.ui.fragment.leaderboard.LeaderboardFragment
import com.example.memoryapp.ui.fragment.menu.MainMenuFragment
import com.example.memoryapp.ui.fragment.splash.SplashFragment
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NavigationTest {
    private lateinit var navController : TestNavHostController

    @Before
    fun createGraph(){
        navController = TestNavHostController(
            ApplicationProvider.getApplicationContext())
        launchActivity<MainActivity>()
    }

    @Test
    fun testNavigationSpalshToMenuScreen(){
        val splashScreen = launchFragmentInContainer<SplashFragment>()
        splashScreen.onFragment{ fragment ->
            navController.setGraph(R.navigation.my_nav)
            Navigation.setViewNavController(fragment.requireView(),navController)
        }
        runBlocking {
            delay(2000L)
        }
        assertEquals(
            navController.currentDestination?.id,
            R.id.mainMenuFragment
        )

    }

    @Test
    fun testNavigationMenuToBottomScreen() {
        val bundle = bundleOf("victory" to false )
        val mainMenuScreen = launchFragmentInContainer<MainMenuFragment>(bundle)
        mainMenuScreen.onFragment{ fragment ->
            navController.setGraph(R.navigation.my_nav)
            navController.setCurrentDestination(R.id.mainMenuFragment,bundle)
            Navigation.setViewNavController(fragment.requireView(),navController)
        }
        onView(ViewMatchers.withId(R.id.play)).perform(ViewActions.click())

        assertEquals(
            navController.currentDestination?.id,
            R.id.bottomDialog
        )
    }

    @Test
    fun testNavigationMenuToLeaderBoard(){
        val bundle = bundleOf("victory" to false )
        val mainMenuScreen = launchFragmentInContainer<MainMenuFragment>(bundle)
        mainMenuScreen.onFragment{ fragment ->
            navController.setGraph(R.navigation.my_nav)
            navController.setCurrentDestination(R.id.mainMenuFragment,bundle)
            Navigation.setViewNavController(fragment.requireView(),navController)
        }
        onView(ViewMatchers.withId(R.id.leaderboards)).perform(ViewActions.click())
        assertEquals(
            navController.currentDestination?.id,
            R.id.leaderboardFragment
        )
    }
    @Test
    fun testNavigationMenuToInstruction(){
        val bundle = bundleOf("victory" to false )
        val mainMenuScreen = launchFragmentInContainer<MainMenuFragment>(bundle)
        mainMenuScreen.onFragment{ fragment ->
            navController.setGraph(R.navigation.my_nav)
            navController.setCurrentDestination(R.id.mainMenuFragment,bundle)
            Navigation.setViewNavController(fragment.requireView(),navController)
        }
        onView(ViewMatchers.withId(R.id.instruction)).perform(ViewActions.click())
        assertEquals(
            navController.currentDestination?.id,
            R.id.instructionFragment
        )
    }
    @Test
    fun testNavigationLeaderboardToMenu(){
        val leaderboardScreen = launchFragmentInContainer<LeaderboardFragment>(themeResId = R.style.Theme_MemoryApp)
        leaderboardScreen.onFragment{ fragment ->
            navController.setGraph(R.navigation.my_nav)
            navController.setCurrentDestination(R.id.leaderboardFragment)
            Navigation.setViewNavController(fragment.requireView(),navController)
        }
        onView(ViewMatchers.withId(R.id.go_back)).perform(ViewActions.click())
        assertEquals(
            navController.currentDestination?.id,
            R.id.mainMenuFragment
        )
    }
    @Test
    fun testNavigationInstructionToMenu(){
        val instructionScreen = launchFragmentInContainer<InstructionFragment>(themeResId = R.style.Theme_MemoryApp)
        instructionScreen.onFragment{ fragment ->
            navController.setGraph(R.navigation.my_nav)
            navController.setCurrentDestination(R.id.instructionFragment)
            Navigation.setViewNavController(fragment.requireView(),navController)
        }
        onView(ViewMatchers.withId(R.id.go_back)).perform(ViewActions.click())
        assertEquals(
            navController.currentDestination?.id,
            R.id.mainMenuFragment
        )
    }

}