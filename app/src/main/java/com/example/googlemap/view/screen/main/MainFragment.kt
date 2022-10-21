package com.example.googlemap.view.screen.main

import android.Manifest
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.googlemap.R
import com.example.googlemap.databinding.FragmentMainBinding
import pub.devrel.easypermissions.EasyPermissions

//https://blog.mindorks.com/implementing-easy-permissions-in-android-android-tutorial

class MainFragment : Fragment() {
    private val TAG = "MainFragment"
    private val RC_LOCATION_PERM = 124

    private lateinit var binding: FragmentMainBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun hasLocationPermissions(): Boolean? {
        return activity?.let {
            EasyPermissions.hasPermissions(
                it,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        }
    }

    fun locationTask() {
        if (hasLocationPermissions() == true) {
            Toast.makeText(activity, "TODO: Location things", Toast.LENGTH_LONG).show()
        } else {
            // Ask for one permission
            EasyPermissions.requestPermissions(
                this,
                getString(R.string.rationale_location),
                RC_LOCATION_PERM,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        }
    }

}