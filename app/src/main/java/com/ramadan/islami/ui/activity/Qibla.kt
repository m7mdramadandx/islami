package com.ramadan.islami.ui.activity


import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.*
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationServices
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.logEvent
import com.ramadan.islami.R
import com.ramadan.islami.utils.ACCESS_FINE_LOCATION_REQ_CODE
import com.ramadan.islami.utils.QIBLA_LATITUDE
import com.ramadan.islami.utils.QIBLA_LONGITUDE
import kotlinx.android.synthetic.main.activity_qibla.*
import kotlin.math.roundToInt

class Qibla : AppCompatActivity() {
    var currentDegree: Float = 0f
    var currentNeedleDegree: Float = 0f
    lateinit var sensorManager: SensorManager
    lateinit var sensor: Sensor
    lateinit var userLocation: Location
    lateinit var needleAnimation: RotateAnimation

    override fun onResume() {
        super.onResume()
        MainActivity.firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW) {
            param(FirebaseAnalytics.Param.SCREEN_NAME, title.toString())
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qibla)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        needleAnimation = RotateAnimation(
            currentNeedleDegree,
            0f,
            Animation.RELATIVE_TO_SELF,
            .5f,
            Animation.RELATIVE_TO_SELF,
            .5f
        )

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
            if (!checkIfAlreadyPermission()) {
                requestForSpecificPermission()
            } else {
                val fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
                fusedLocationClient.lastLocation.addOnSuccessListener {
                    initQiblaDirection(it.latitude, it.longitude)
                }
                fusedLocationClient.lastLocation.addOnFailureListener {
                    Toast.makeText(this, it.localizedMessage, Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    private fun checkIfAlreadyPermission(): Boolean {
        val result =
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
        return result == PackageManager.PERMISSION_GRANTED
    }

    private fun requestForSpecificPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), ACCESS_FINE_LOCATION_REQ_CODE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        when (requestCode) {
            ACCESS_FINE_LOCATION_REQ_CODE -> if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "guides", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, getString(R.string.couldnotDownload), Toast.LENGTH_LONG)
                    .show()
            }
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    private fun initQiblaDirection(latitude: Double, longitude: Double) {
        userLocation = Location("User Location")
        userLocation.latitude = latitude
        userLocation.longitude = longitude
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION)
        sensorManager.registerListener(object : SensorEventListener {
            override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
            }

            @SuppressLint("SetTextI18n")
            override fun onSensorChanged(sensorEvent: SensorEvent) {
                val degree: Float = sensorEvent.values[0].roundToInt().toFloat()
                var head: Float = sensorEvent.values[0].roundToInt().toFloat()
                val destLocation = Location("Destination Location")
                destLocation.latitude = QIBLA_LATITUDE
                destLocation.longitude = QIBLA_LONGITUDE
                var bearTo = userLocation.bearingTo(destLocation)
                val geoField = GeomagneticField(
                    userLocation.latitude.toFloat(),
                    userLocation.longitude.toFloat(),
                    userLocation.altitude.toFloat(),
                    System.currentTimeMillis()
                )
                head -= geoField.declination
                if (bearTo < 0) bearTo += 360
                var direction = bearTo - head
                if (direction < 0) direction += 360
                txtDegrees.text = "$degree degrees"
                needleAnimation = RotateAnimation(
                    currentNeedleDegree,
                    direction,
                    Animation.RELATIVE_TO_SELF,
                    .5f,
                    Animation.RELATIVE_TO_SELF,
                    .5f
                )
                needleAnimation.fillAfter = true
                needleAnimation.duration = 200
                imgCompass.startAnimation(needleAnimation)
                currentNeedleDegree = direction
                currentDegree = -degree
            }
        }, sensor, SensorManager.SENSOR_DELAY_GAME)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}