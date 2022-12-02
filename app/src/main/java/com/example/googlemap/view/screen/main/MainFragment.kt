package com.example.googlemap.view.screen.main

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import com.example.googlemap.R
import com.example.googlemap.databinding.FragmentMainBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import org.osmdroid.config.Configuration.*
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.gestures.RotationGestureOverlay
import pub.devrel.easypermissions.EasyPermissions


class MainFragment : Fragment(), EasyPermissions.PermissionCallbacks,
    EasyPermissions.RationaleCallbacks {
    private val TAG = "MainFragment"
    private val RC_LOCATION_PERM = 124
    private lateinit var fusedLocationClient: FusedLocationProviderClient

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
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
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
            initMap()
        } else {
            EasyPermissions.requestPermissions(
                this,
                getString(R.string.rationale_location),
                RC_LOCATION_PERM,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            locationTask()
        }
    }

    @SuppressLint("MissingPermission")
    private fun initMap() {
        getInstance().load(
            activity,
            PreferenceManager.getDefaultSharedPreferences(requireActivity())
        )
        binding.map.setTileSource(TileSourceFactory.MAPNIK)
        val mapController = binding.map.controller
        mapController.setZoom(9.5)

        binding.map.setMultiTouchControls(true)

        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->
                if (location != null) {
                    mapController.animateTo(GeoPoint(location))
                }
            }



        val mRotationGestureOverlay = RotationGestureOverlay(binding.map)
        mRotationGestureOverlay.isEnabled = true
        binding.map.overlays.add(mRotationGestureOverlay)
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