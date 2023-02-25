package com.example.composition.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.composition.R
import com.example.composition.databinding.FragmentGameBinding
import com.example.composition.domain.entity.GameResult
import com.example.composition.domain.entity.GameSettings
import com.example.composition.domain.entity.Level

class GameFragment : Fragment() {

	private lateinit var level: Level
	private val viewModel by lazy {
		ViewModelProvider(
			this,
			ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
		) [GameViewModel::class.java]
	}

	private var _binding: FragmentGameBinding? = null
	private val binding: FragmentGameBinding
	get() = _binding ?: throw RuntimeException("FragmentGameBinding = null")

	companion object {

		private const val KEY_LEVEL = "level"
		const val GAME_FRAGMENT_NAME = "GameFragment"


		fun newInstance(level: Level): GameFragment {
			return GameFragment().apply {
				arguments = Bundle().apply {
					putParcelable(KEY_LEVEL, level)
				}
			}
		}
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
		viewModel.startGame(level)
		viewModel.formattedTime.observe(viewLifecycleOwner) {
			binding.tvTimer.text = it
		}
		viewModel.gameResult.observe(viewLifecycleOwner) {
			requireActivity().supportFragmentManager.beginTransaction()
				.replace(R.id.main_container, GameFinishedFragment.newInstance(it))
				.addToBackStack(null)
				.commit()
		}

		viewModel.question.observe(viewLifecycleOwner) {
			with(binding) {
				tvLeftNumber.text = it.visibleNumber.toString()
				tvSum.text = it.sum.toString()
				tvOption1.text = it.options[0].toString()
				tvOption2.text = it.options[1].toString()
				tvOption3.text = it.options[2].toString()
				tvOption4.text = it.options[3].toString()
				tvOption5.text = it.options[4].toString()
				tvOption6.text = it.options[5].toString()
			}
		}

		with(binding) {
			tvOption1.setOnClickListener {
				viewModel.chooseAnswer(tvOption1.text.toString().toInt())
			}
			tvOption2.setOnClickListener {
				viewModel.chooseAnswer(tvOption2.text.toString().toInt())
			}
			tvOption3.setOnClickListener {
				viewModel.chooseAnswer(tvOption3.text.toString().toInt())
			}
			tvOption4.setOnClickListener {
				viewModel.chooseAnswer(tvOption4.text.toString().toInt())
			}
			tvOption5.setOnClickListener {
				viewModel.chooseAnswer(tvOption5.text.toString().toInt())
			}
			tvOption6.setOnClickListener {
				viewModel.chooseAnswer(tvOption6.text.toString().toInt())
			}
		}

		viewModel.progressAnswers.observe(viewLifecycleOwner) {
			binding.tvAnswersProgress.text = it
		}

//		binding.tvLeftNumber.setOnClickListener {
//			requireActivity().supportFragmentManager.beginTransaction()
//				.replace(R.id.main_container, GameFinishedFragment.newInstance())
//				.addToBackStack(null)
//				.commit()
//		}
	}

	private fun parseArgs() {
		requireArguments().getParcelable<Level>(KEY_LEVEL)?.let {
			level = it
		}
	}

	private fun launchGameFinishedFragment(gameResult: GameResult) {
		requireActivity().supportFragmentManager.beginTransaction()
			.replace(R.id.main_container, GameFinishedFragment.newInstance(gameResult))
			.addToBackStack(null)
			.commit()
	}

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}
}