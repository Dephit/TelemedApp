package com.app.telemed.fragments

import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.navGraphViewModels
import com.app.telemed.viewModels.LessonQuieteningViewModel
import com.app.telemed.R
import com.app.telemed.databinding.LessonQuesteningFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LessonQuestioningFragment : QuestionFragment() {

    lateinit var binding: LessonQuesteningFragmentBinding

    override val viewModel: LessonQuieteningViewModel by navGraphViewModels(R.id.menu_navigation_xml) {
        defaultViewModelProviderFactory
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
            LessonQuesteningFragmentBinding.inflate(inflater).run {
                binding = this
                root
            }

    override fun restoreState(savedInstanceState: Bundle?) {

    }

    override fun onStateRestored(question: Question) {

    }

    override fun setListeners() {
        binding.toQuestions.setOnClickListener {
            navigateToQuestion()
        }
    }

    override fun manageLoading(b: Boolean) {

    }

    override fun <T> manageSuccess(obj: T?) {

    }

    override fun manageError(bool: Boolean) {

    }

}

sealed class QuestionType(){
    object Mark: QuestionType()
    object Select: QuestionType()
    object Comment: QuestionType()
}

data class Question(
        val question: String = "",
        val type: QuestionType,
        val variants: List<String>? = listOf(),
        var comment: String = ""
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString().toString(),
            when {
                QuestionType.Comment.javaClass.name == parcel.readString() -> QuestionType.Comment
                QuestionType.Mark.javaClass.name == parcel.readString() -> QuestionType.Mark
                else -> QuestionType.Select
            },
            parcel.createStringArrayList(),
            parcel.readString().toString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(question)
        parcel.writeString(type.javaClass.name)
        parcel.writeStringList(variants)
        parcel.writeString(comment)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Question> {
        override fun createFromParcel(parcel: Parcel): Question {
            return Question(parcel)
        }

        override fun newArray(size: Int): Array<Question?> {
            return arrayOfNulls(size)
        }
    }
}