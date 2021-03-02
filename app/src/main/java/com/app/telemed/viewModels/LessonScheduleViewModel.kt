package com.app.telemed.viewModels

import android.util.Log
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.app.telemed.interfaces.Repository
import com.app.telemed.viewModels.baseViewModels.BaseViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class LessonScheduleViewModel @ViewModelInject constructor(
    private val repository: Repository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : BaseViewModel(repository, savedStateHandle){

    fun updateProfile() {
        viewModelScope.launch {
            Log.i("PROFILE_LOAD", "loading started...")
            repository.getGoals()
                .onStart { Log.i("GOAL_LOAD", "loading started...")  }
                .catch { Log.e("GOAL_LOAD", it.message.toString()) }
                .collect { Log.i("GOAL_LOAD", "loading finished... \n$it") }
            repository.getProfile()
                    .onStart { Log.i("PROFILE_LOAD", "loading started...")  }
                    .catch { Log.e("PROFILE_LOAD", it.message.toString()) }
                    .collect { Log.i("PROFILE_LOAD", "loading finished... \n$it") }
        }
    }

}