package com.app.telemed

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.app.telemed.interfaces.Lesson
import com.app.telemed.interfaces.Repository
import com.app.telemed.viewModels.baseViewModels.BaseViewModel
import com.app.telemed.viewModels.baseViewModels.ModelState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.*

class LessonInfoViewModel @ViewModelInject constructor(
    override val repository: Repository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : LessonBaseViewModel(repository, savedStateHandle){

    fun setLesson(lesson: Lesson?) {
        postLesson(lesson)
    }

}

