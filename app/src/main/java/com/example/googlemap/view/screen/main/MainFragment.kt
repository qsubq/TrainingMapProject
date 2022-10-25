package com.example.googlemap.view.screen.main

import android.Manifest
import android.os.Bundle
import androidx.preference.PreferenceManager
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.googlemap.R
import com.example.googlemap.databinding.FragmentMainBinding
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import org.osmdroid.config.Configuration.*

class MainFragment : Fragment(), EasyPermissions.PermissionCallbacks,
    EasyPermissions.RationaleCallbacks {
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
        locationTask()
    }

    private fun hasLocationPermissions(): Boolean? {
        return activity?.let {
            EasyPermissions.hasPermissions(
                it,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        }
    }

    private fun locationTask() {
        if (hasLocationPermissions() == true) {

            getInstance().load(activity, PreferenceManager.getDefaultSharedPreferences(requireActivity()))

            binding.map.setTileSource(TileSourceFactory.MAPNIK)
            val mapController = binding.map.controller
            mapController.setZoom(9.5)
            val startPoint = GeoPoint(48.8583, 2.2944)
            mapController.animateTo(startPoint)
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

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        Log.d(TAG, "onPermissionsGranted:" + requestCode + ":" + perms.size)
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        Log.d(TAG, "onPermissionsDenied:" + requestCode + ":" + perms.size)

        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            EasyPermissions.requestPermissions(
                this,
                getString(R.string.rationale_location),
                RC_LOCATION_PERM,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        }
    }

    override fun onRationaleAccepted(requestCode: Int) {
        Log.d(TAG, "onRationaleAccepted:" + requestCode)
    }

    override fun onRationaleDenied(requestCode: Int) {
        Log.d(TAG, "onRationaleDenied:" + requestCode)
    }

    override fun onPause() {
        super.onPause()
        binding.map.onPause()
    }

    override fun onResume() {
        super.onResume()
        binding.map.onResume()
    }
}