package com.app.telemed.viewModels

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.telemed.interfaces.Repository
import com.app.telemed.models.GoalResponse
import com.app.telemed.models.Profile
import com.app.telemed.viewModels.baseViewModels.BaseViewModel
import com.app.telemed.viewModels.baseViewModels.ModelState
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@ExperimentalCoroutinesApi
class CommonDataViewModel @ViewModelInject constructor(
    val repository: Repository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : BaseViewModel(repository, savedStateHandle) {

    private lateinit var profile: Profile

    fun getGoal(goalId: String): CharSequence? {
        return repository.getDB().goalResponseDao().get()?.data?.find { it.id == goalId.toInt() }?.name
    }

    fun getGoal(): GoalResponse? {
        return repository.getDB().goalResponseDao().get()
    }

    fun updateProfile(
        name: String,
        surname: String,
        height: Int,
        weight: Int,
        age: Int,
        gender: String,
        goalId: Int
    ) {
        viewModelScope.launch {
            repository
                .updateProfile(name = name, surname = surname,height = height, weight =  weight, age =  age, gender =  gender, goal_id = goalId, email = profile.data.email, phone = profile.data.phone)
                .onStart { modelState.value = ModelState.Loading }
                .onStart { modelState.value = ModelState.Loading }
                .catch { modelState.value = ModelState.Error(it.message) }
                .collect { modelState.value = ModelState.Success(it) }

        }
    }

    init {
        viewModelScope.launch {
            withContext(IO) {

                repository.getDB().profileDao().get()
                    .flowOn(IO)
                    .onStart { modelState.value = ModelState.Loading }
                    .catch { modelState.value = ModelState.Error(it.message) }
                    .collect {
                        if (it == null) {
                            repository.getProfile()
                        } else {
                            profile = it
                            modelState.value = ModelState.Success(it)
                        }
                    }
            }
        }
    }
}