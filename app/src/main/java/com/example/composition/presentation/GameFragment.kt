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

	private val tvOptions by lazy {
		mutableListOf<TextView>().apply {
			with(binding) {
				add(tvOption1)
				add(tvOption2)
				add(tvOption3)
				add(tvOption4)
				add(tvOption5)
				add(tvOption6)
			}
		}
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
		setOptionsListeners()
	}

	private fun setOptionsListeners() {
		for (option in tvOptions) {
			option.setOnClickListener {
				viewModel.chooseAnswer(option.text.toString().toInt())
			}
		}
	}

	private fun observeViewModel() {
		viewModel.question.observe(viewLifecycleOwner) {
			binding.tvSum.text = it.sum.toString()
			binding.tvLeftNumber.text = it.visibleNumber.toString()
			for (i in 0 until tvOptions.size) {
				tvOptions[i].text = it.options[i].toString()
			}
		}
		viewModel.percentOfRightAnswers.observe(viewLifecycleOwner) {
			binding.progressBar.setProgress(it, true)
		}
		viewModel.progressAnswers.observe(viewLifecycleOwner) {
			binding.tvAnswersProgress.text = it
		}
		viewModel.enoughCountOfRightAnswers.observe(viewLifecycleOwner) {
			binding.tvAnswersProgress.setTextColor(getColorByState(it))
		}
		viewModel.enoughPercentOfRightAnswers.observe(viewLifecycleOwner) {
			binding.progressBar.progressTintList = ColorStateList.valueOf(getColorByState(it))
		}
		viewModel.formattedTime.observe(viewLifecycleOwner) {
			binding.tvTimer.text = it
		}
		viewModel.minPercent.observe(viewLifecycleOwner) {
			binding.progressBar.secondaryProgress = it
		}
		viewModel.gameResult.observe(viewLifecycleOwner) {
			launchGameFinishedFragment(it)
		}
	}

	private fun getColorByState(state: Boolean): Int {
		val getColorId = if (state) {
			android.R.color.holo_green_light
		} else {
			android.R.color.holo_red_light
		}
		return ContextCompat.getColor(requireContext(), getColorId)
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