package com.app.telemed.viewModels

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import com.app.telemed.interfaces.Lesson
import com.app.telemed.interfaces.Repository

class LessonInfoViewModel @ViewModelInject constructor(
    override val repository: Repository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : LessonBaseViewModel(repository, savedStateHandle){

    fun setLesson(lesson: Lesson?) {
        postLesson(lesson)
    }

}

