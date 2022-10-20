package com.example.googlemap.view.screen.onboarding

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.googlemap.R
import com.example.googlemap.databinding.FragmentOnBoardingBinding
import java.util.*


class OnBoardingFragment : Fragment() {
    private lateinit var binding: FragmentOnBoardingBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentOnBoardingBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val queue: Queue<Int> =
            LinkedList(listOf(R.drawable.number1, R.drawable.number2, R.drawable.number3))
        queue.poll()?.let { binding.imgView.setImageResource(it) }

        binding.btnNext.setOnClickListener {
            if (binding.btnNext.hint == "Начать") {
                this.findNavController().navigate(R.id.action_onBoardingFragment_to_mainFragment)
                val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
                if (sharedPref != null) {
                    with(sharedPref.edit()) {
                        putBoolean(getString(R.string.saved_onBoarding_bool), true)
                        apply()
                    }
                }

            }
            queue.poll()?.let { binding.imgView.setImageResource(it) }
            if (queue.isEmpty()) {
                binding.btnNext.hint = "Начать"
            }
        }
    }
}