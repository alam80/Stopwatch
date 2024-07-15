package com.alam.stopwatch

import android.os.Bundle
import android.os.SystemClock
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var timerText: TextView
    private lateinit var startButton: Button
    private lateinit var pauseButton: Button
    private lateinit var resetButton: Button

    private var startTime: Long = 0
    private var pauseTime: Long = 0
    private var isRunning: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        timerText = findViewById(R.id.timer_text)
        startButton = findViewById(R.id.start_button)
        pauseButton = findViewById(R.id.pause_button)
        resetButton = findViewById(R.id.reset_button)

        startButton.setOnClickListener {
            if (!isRunning) {
                startTime = SystemClock.elapsedRealtime()
                isRunning = true
                startTimer()
            }
        }

        pauseButton.setOnClickListener {
            if (isRunning) {
                pauseTime = SystemClock.elapsedRealtime()
                isRunning = false
                pauseTimer()
            }
        }

        resetButton.setOnClickListener {
            startTime = 0
            pauseTime = 0
            isRunning = false
            timerText.text = "00:00:00"
        }
    }

    private fun startTimer() {
        Thread {
            while (isRunning) {
                val currentTime = SystemClock.elapsedRealtime() - startTime
                val minutes = currentTime / 60000
                val seconds = (currentTime % 60000) / 1000
                val milliseconds = (currentTime % 1000)

                runOnUiThread {
                    timerText.text = String.format("%02d:%02d:%03d", minutes, seconds, milliseconds)
                }

                Thread.sleep(10)
            }
        }.start()
    }

    private fun pauseTimer() {
        val pauseDuration = pauseTime - startTime
        startTime = SystemClock.elapsedRealtime()
        startTime -= pauseDuration
    }
}