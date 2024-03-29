package com.example.composition.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.composition.R
import com.example.composition.databinding.FragmentChooseLevelBinding
import com.example.composition.domain.entity.Level

class ChooseLevelFragment : Fragment() {

	private var _binding: FragmentChooseLevelBinding? = null
	private val binding: FragmentChooseLevelBinding
		get() = _binding ?: throw RuntimeException("ChooseLevelFragment = null")

	companion object {

		const val CHOOSE_LEVEL_NAME = "ChooseLevelFragment"

		fun newInstance(): ChooseLevelFragment {
			return ChooseLevelFragment()
		}
	}

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		_binding = FragmentChooseLevelBinding.inflate(inflater, container, false)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		with(binding) {
			buttonLevelTest.setOnClickListener {
				launchGameFragment(Level.TEST)
			}
			buttonLevelEasy.setOnClickListener {
				launchGameFragment(Level.EASY)
			}
			buttonLevelNormal.setOnClickListener {
				launchGameFragment(Level.NORMAL)
			}
			buttonLevelHard.setOnClickListener {
				launchGameFragment(Level.HARD)
			}
		}
	}

	private fun launchGameFragment(level: Level) {
		val args = Bundle().apply {
			putParcelable(GameFragment.KEY_LEVEL, level)
		}
		findNavController().navigate(R.id.action_chooseLevelFragment_to_gameFragment, args)
	}

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}

}