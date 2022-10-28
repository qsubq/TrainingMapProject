package com.example.googlemap.view.screen.containerFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.googlemap.R
import com.example.googlemap.databinding.FragmentContainerBinding
import com.google.android.material.tabs.TabLayoutMediator

class ContainerFragment : Fragment() {
    private lateinit var binding: FragmentContainerBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentContainerBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewPager.adapter = MainAdapter(requireActivity())

        TabLayoutMediator(binding.tbLayout,binding.viewPager){tab,position ->
            if(position == 1) tab.text = "Advert"
            else tab.text = "Map"
        }.attach()
    }
}