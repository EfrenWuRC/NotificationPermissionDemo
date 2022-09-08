package com.glip.notificationpermissiondemo

import android.Manifest
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat


class MainActivity : AppCompatActivity() {

    private val notificationManager: NotificationManager by lazy(LazyThreadSafetyMode.NONE) {
        getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }
    private val enterSecondScreenRunnable by lazy(LazyThreadSafetyMode.NONE) {
        Runnable {
            startActivity(Intent(this, SecondActivity::class.java))
        }
    }
    private val mainHandler = Handler(Looper.getMainLooper())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.start_button)?.setOnClickListener {
            mainHandler.removeCallbacks(enterSecondScreenRunnable)
            startActivity(Intent(this, SecondActivity::class.java))
        }
        findViewById<Button>(R.id.permission_button)?.setOnClickListener {
            startRequestPermission()
        }
    }

    private fun startRequestPermission() {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.S_V2 || notificationManager.areNotificationsEnabled()) {
            mainHandler.postDelayed(enterSecondScreenRunnable, 3000L)
        } else {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_DENIED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(
                        Manifest.permission.POST_NOTIFICATIONS
                    ),
                    101
                )
                mainHandler.postDelayed(enterSecondScreenRunnable, 3000L)
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode === 101) {
            var isAllGranted = true
            for (grant in grantResults) {
                if (grant != PackageManager.PERMISSION_GRANTED) {
                    isAllGranted = false
                    break
                }
            }
            if (isAllGranted) {
                Log.d("MainActivity", "granted")
            } else {
                Log.d("MainActivity", "not granted")
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mainHandler.removeCallbacks(enterSecondScreenRunnable)
    }
}