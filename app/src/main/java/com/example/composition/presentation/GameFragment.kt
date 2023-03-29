package com.example.composition.presentation

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.composition.R
import com.example.composition.databinding.FragmentGameBinding
import com.example.composition.domain.entity.GameResult
import com.example.composition.domain.entity.Level

class GameFragment : Fragment() {

	private lateinit var level: Level
	private val viewModel by lazy {
		ViewModelProvider(
			this,
			GameViewModelFactory(level, requireActivity().application)
		)[GameViewModel::class.java]
	}

	private var _binding: FragmentGameBinding? = null
	private val binding: FragmentGameBinding
		get() = _binding ?: throw RuntimeException("FragmentGameBinding = null")

	companion object {

		const val KEY_LEVEL = "level"
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		parseArgs()
	}

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		_binding = FragmentGameBinding.inflate(inflater, container, false)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		binding.viewModel = viewModel
		binding.lifecycleOwner = viewLifecycleOwner
		observeViewModel()
	}

	private fun observeViewModel() {
		viewModel.gameResult.observe(viewLifecycleOwner) {
			launchGameFinishedFragment(it)
		}
	}

	private fun parseArgs() {
		requireArguments().getParcelable<Level>(KEY_LEVEL)?.let {
			level = it
		}
	}

	private fun launchGameFinishedFragment(gameResult: GameResult) {
		val args = Bundle().apply {
			putParcelable(GameFinishedFragment.GAME_RESULT, gameResult)
		}
		findNavController().navigate(R.id.action_gameFragment_to_gameFinishedFragment, args)
	}

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}
}