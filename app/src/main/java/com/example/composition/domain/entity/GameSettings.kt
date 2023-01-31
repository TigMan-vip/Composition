package com.example.composition.domain.entity

import android.os.Parcelable
import java.io.Serializable

@
data class GameSettings(
	val maxSumValue: Int,
	val minCountOfRightAnswers: Int,
	val minPercentOfRightAnswer: Int,
	val gameTimeInSeconds: Int,
): Parcelable
