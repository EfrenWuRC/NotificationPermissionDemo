package com.glip.notificationpermissiondemo

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat


class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        findViewById<Button>(R.id.toast_button)?.setOnClickListener {
            startActivity(Intent(this, ThirdActivity::class.java))
        }
        findViewById<Button>(R.id.permission_button)?.setOnClickListener {
            startRequestPermission()
        }
    }

    private fun startRequestPermission() {

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.RECORD_AUDIO
            ) == PackageManager.PERMISSION_DENIED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.RECORD_AUDIO
                ),
                101
            )
        }
    }

}