package com.example.composition.presentation

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.composition.R
import com.example.composition.domain.entity.GameResult

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