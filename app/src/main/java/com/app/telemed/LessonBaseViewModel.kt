package com.app.telemed

import androidx.lifecycle.SavedStateHandle
import com.app.telemed.interfaces.BaseViewModelInterface
import com.app.telemed.interfaces.Lesson
import com.app.telemed.interfaces.Repository
import com.app.telemed.viewModels.baseViewModels.BaseViewModel
import com.app.telemed.viewModels.baseViewModels.ModelState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.*

open class LessonBaseViewModel(repository: Repository, savedStateHandle: SavedStateHandle): BaseViewModel(repository, savedStateHandle), ILessonViewModel  {

    var daysLeft = 0
    var hoursLeft = 0
    var minutesLeft = 0

    fun setTimer(lesson: Lesson){
        daysLeft = Calendar.getInstance().get(Calendar.DAY_OF_YEAR) - lesson.date.get(
            Calendar.DAY_OF_YEAR)
        hoursLeft = Calendar.getInstance().get(Calendar.HOUR) - lesson.date.get(Calendar.HOUR)
        minutesLeft = Calendar.getInstance().get(Calendar.MINUTE) - lesson.date.get(Calendar.MINUTE)
    }

    override suspend fun getTimer(): Flow<String> {
        return flow {
            while (true) {
                when {
                    minutesLeft > 0 -> {
                        minutesLeft--
                    }
                    hoursLeft > 0 -> {
                        minutesLeft = 59
                        hoursLeft--
                    }
                    daysLeft > 0 -> {
                        minutesLeft = 59
                        hoursLeft = 23
                        daysLeft--
                    }
                }
                val time = String.format(
                    "Осталось %2d дн. %2d ч. %02d м.",
                    daysLeft, hoursLeft, minutesLeft
                )

                emit(time)
                delay(1000)
            }
        }
    }
}

interface ILessonViewModel: BaseViewModelInterface {
    suspend fun getTimer(): Flow<String>
}
