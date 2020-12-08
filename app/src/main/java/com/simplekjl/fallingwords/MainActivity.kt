package com.simplekjl.fallingwords

import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        object : CountDownTimer(3000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val number = millisUntilFinished / 1000
                startCounter.text = number.toString()
            }

            override fun onFinish() {
                startCounter.text = getString(R.string.start_label)
                // TODO notify viewModel to start game and hide views
            }
        }.start()
    }
}
