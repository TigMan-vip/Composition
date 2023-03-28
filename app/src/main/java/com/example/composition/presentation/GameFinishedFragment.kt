package com.example.composition.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.composition.R
import com.example.composition.databinding.FragmentGameFinishedBinding
import com.example.composition.domain.entity.GameResult

class GameFinishedFragment : Fragment() {

	private lateinit var args: GameResult

	private var _binding: FragmentGameFinishedBinding? = null
	private val binding: FragmentGameFinishedBinding
		get() = _binding ?: throw RuntimeException("FragmentGameFinishedBinding = null")

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		parseArgs()
	}

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		_binding = FragmentGameFinishedBinding.inflate(inflater, container, false)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		binding.buttonRetry.setOnClickListener {
			retryGame()
		}
		binding.gameResult = args
	}

	companion object {

		const val GAME_RESULT = "game_result"
	}

	private fun parseArgs() {
		requireArguments().getParcelable<GameResult>(GAME_RESULT)?.let {
			args = it
		}
	}

	private fun retryGame() {
		findNavController().popBackStack()
	}

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}
}