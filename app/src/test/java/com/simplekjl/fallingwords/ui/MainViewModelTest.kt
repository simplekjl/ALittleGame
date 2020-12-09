package com.simplekjl.fallingwords.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.*
import com.simplekjl.fallingwords.data.TranslationRepository
import com.simplekjl.fallingwords.ui.model.MainScreenView
import com.simplekjl.fallingwords.ui.model.ScreenState
import com.simplekjl.fallingwords.ui.model.WordViewEntity
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers.isA
import org.mockito.Mock
import org.mockito.MockitoAnnotations

internal class MainViewModelTest {


    @Rule
    @JvmField
    val ruleForLiveData = InstantTaskExecutorRule()

    @Mock
    lateinit var mockLiveDataObserver: Observer<ScreenState>

    private lateinit var viewModel: MainViewModel
    private val mockRepository: TranslationRepository = mock()
    private val mockSetOfWords: List<WordViewEntity> = mock()

    private val list: List<WordViewEntity> by lazy { createListOfRandomWords() }

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        viewModel = MainViewModel(mockRepository)
        viewModel.screenState.observeForever(mockLiveDataObserver)
    }

    // Given the random values to create the game some test have to be re done l
    @Test
    fun shuffleWords() {

        whenever(mockRepository.getSetOfWords()).thenReturn(list)
        viewModel.shuffleWords()
        verify(mockLiveDataObserver, times(1)).onChanged(isA(MainScreenView::class.java))
        verifyNoMoreInteractions(mockLiveDataObserver)
    }

    @Test
    fun `when user clicks negative btn and the word was a real translation`() {
        whenever(mockRepository.getSetOfWords()).thenReturn(list)
        viewModel.checkFalse(true, false)
        verify(mockLiveDataObserver, times(1)).onChanged(isA(MainScreenView::class.java))
        assertThat((viewModel.screenState.value as MainScreenView).score).isEqualTo(0)
        verifyNoMoreInteractions(mockLiveDataObserver)
    }


    @Test
    fun `when user clicks negative btn and the word was a false translation`() {
        whenever(mockRepository.getSetOfWords()).thenReturn(list)
        viewModel.checkFalse(false, false)
        verify(mockLiveDataObserver, times(1)).onChanged(isA(MainScreenView::class.java))
        assertThat((viewModel.screenState.value as MainScreenView).score).isEqualTo(1)
        verifyNoMoreInteractions(mockLiveDataObserver)
    }


    @Test
    fun `when user clicks positive btn and the word was a false translation`() {
        whenever(mockRepository.getSetOfWords()).thenReturn(list)
        viewModel.checkValid(false, true)
        verify(mockLiveDataObserver, times(1)).onChanged(isA(MainScreenView::class.java))
        assertThat((viewModel.screenState.value as MainScreenView).score).isEqualTo(0)
        verifyNoMoreInteractions(mockLiveDataObserver)
    }

    @Test
    fun `when user clicks positive btn and the word was a true translation`() {
        whenever(mockRepository.getSetOfWords()).thenReturn(list)
        viewModel.checkValid(true, true)
        verify(mockLiveDataObserver, times(1)).onChanged(isA(MainScreenView::class.java))
        assertThat((viewModel.screenState.value as MainScreenView).score).isEqualTo(1)
        verifyNoMoreInteractions(mockLiveDataObserver)
    }


    @Test
    fun `when user restart the game`() {
        whenever(mockRepository.getSetOfWords()).thenReturn(list)
        viewModel.restart()
        verify(mockLiveDataObserver, times(1)).onChanged(isA(MainScreenView::class.java))
        assertThat((viewModel.screenState.value as MainScreenView).score).isEqualTo(0)
        assertThat((viewModel.screenState.value as MainScreenView).remainingWords).isEqualTo(14)
        verifyNoMoreInteractions(mockLiveDataObserver)
    }

    private fun createListOfRandomWords(): List<WordViewEntity> {
        val list = mutableListOf<WordViewEntity>()
        for (i in 0..16) {
            list.add(WordViewEntity("", ""))
        }
        return list
    }
}
