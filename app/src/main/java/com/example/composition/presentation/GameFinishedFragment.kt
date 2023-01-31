package com.example.composition.presentation

import android.os.Binder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.composition.R
import com.example.composition.databinding.FragmentGameFinishedBinding
import com.example.composition.domain.entity.GameResult

class GameFinishedFragment : Fragment() {

	private lateinit var gameResult: GameResult

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
		requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
			override fun handleOnBackPressed() {
				retryGame()
			}
		})
	}

	companion object {

		private const val GAME_RESULT = "game_result"

		fun newInstance(gameResult: GameResult): GameFinishedFragment {
			return GameFinishedFragment().apply {
				arguments = Bundle().apply {
					putSerializable(GAME_RESULT, gameResult)
				}
			}
		}
	}

	private fun parseArgs() {
		gameResult = requireArguments().getSerializable(GAME_RESULT) as GameResult
	}

	private fun retryGame() {
		requireActivity().supportFragmentManager.popBackStack(GameFragment.GAME_FRAGMENT_NAME, FragmentManager.POP_BACK_STACK_INCLUSIVE)
	}

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}
}