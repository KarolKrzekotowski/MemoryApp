package com.example.memoryapp

import androidx.core.os.bundleOf
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.memoryapp.ui.activities.MainActivity
import com.example.memoryapp.ui.fragment.menu.MainMenuFragment
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MusicTest {

    private lateinit var navController : TestNavHostController

    @Before
    fun createGraph(){
        navController = TestNavHostController(
            ApplicationProvider.getApplicationContext())
        launchActivity<MainActivity>()
    }

    @Test
    fun startMusicStopMusic(){
        val bundle = bundleOf("victory" to false )
        val mainMenuScreen = launchFragmentInContainer<MainMenuFragment>(bundle)
        mainMenuScreen.onFragment{ fragment ->
            navController.setGraph(R.navigation.my_nav)
            navController.setCurrentDestination(R.id.mainMenuFragment,bundle)
            Navigation.setViewNavController(fragment.requireView(),navController)
        }
        Espresso.onView(ViewMatchers.withId(R.id.music)).perform(ViewActions.click())
        Assert.assertEquals(
            MainActivity.mediaPlayer.isPlaying,
            true
        )
        Espresso.onView(ViewMatchers.withId(R.id.music)).perform(ViewActions.click())
        Assert.assertEquals(
            MainActivity.mediaPlayer.isPlaying,
            false
        )
    }

}