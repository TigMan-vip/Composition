package com.example.composition.presentation

import android.content.Context
import android.content.res.ColorStateList
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.example.composition.R
import com.example.composition.domain.entity.GameResult

interface OnOptionClickListener {

	fun onOptionClick(option: Int)
}

//GameFinishedFragment
@BindingAdapter("requiredAnswers")
fun bindRequireAnswer(textView: TextView, count: Int) {
	textView.text = String.format(
		textView.context.getString(R.string.required_score),
		count
	)
}

@BindingAdapter("requiredPercents")
fun bindRequirePercent(textView: TextView, percent: Int) {
	textView.text = String.format(
		textView.context.getString(R.string.required_percentage),
		percent
	)
}

@BindingAdapter("answersCount")
fun bindAnswerCount(textView: TextView, count: Int) {
	textView.text = String.format(
		textView.context.getString(R.string.score_answers),
		count
	)
}

@BindingAdapter("percentRightAnswers")
fun bindPercentRightAnswers(textView: TextView, gameResult: GameResult) {
	textView.text = String.format(
		textView.context.getString(R.string.score_percentage),
		getPercentOfRightAnswers(gameResult)
	)
}

@BindingAdapter("resultEmoji")
fun bindResultEmoji(imageView: ImageView, winner: Boolean) {
		imageView.setImageResource(getSmileResID(winner))
}

private fun getPercentOfRightAnswers(gameResult: GameResult): Int = with(gameResult) {
	if (countOfQuestions == 0) {
		return 0
	} else {
		return ((countOfRightAnswers / countOfQuestions.toDouble()) * 100).toInt()
	}
}

private fun getSmileResID(winner: Boolean): Int {
	return if (winner) {
		R.drawable.ic_smile
	} else {
		R.drawable.ic_sad
	}
}

//GameFragment
@BindingAdapter("setSum")
fun bindSumCount(textView: TextView, sum: Int) {
	textView.text = sum.toString()
}

@BindingAdapter("setVisibleNumber")
fun bindVisibleNumber(textView: TextView, leftNumber: Int) {
	textView.text = leftNumber.toString()
}

@BindingAdapter("setAnswersProgressTextColor")
fun bindAnswersProgressTextColor(textView: TextView, enoughCountOfRightAnswers: Boolean) {
	textView.setTextColor(getColorByState(textView.context, enoughCountOfRightAnswers))
}

@BindingAdapter("setProgressBarColor")
fun bindProgressBarColor(progressBar: ProgressBar, enoughPercentOfRightAnswers: Boolean) {
	progressBar.progressTintList = ColorStateList.valueOf(
		getColorByState(
			progressBar.context,
			enoughPercentOfRightAnswers
		)
	)
}

@BindingAdapter("onOptionClickListener")
fun bindOptionClickListener(textView: TextView, onOptionClickListener: OnOptionClickListener) {
	textView.setOnClickListener {
		onOptionClickListener.onOptionClick(textView.text.toString().toInt())
	}
}

private fun getColorByState(context: Context, state: Boolean): Int {
	val getColorId = if (state) {
		android.R.color.holo_green_light
	} else {
		android.R.color.holo_red_light
	}
	return ContextCompat.getColor(context, getColorId)
}