package com.piendop.eggtimerkotlin

import android.media.MediaPlayer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.SeekBar
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.concurrent.timer
import com.piendop.eggtimerkotlin.R.id.timerSeekBar



class MainActivity : AppCompatActivity() {

    var counterIsActive = false
    lateinit var countDownTimer: CountDownTimer

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_main)
        super.onCreate(savedInstanceState)


        timerSeekBar.progress = 30//default is 30 seconds
        timerSeekBar.max = 600//5 minutes


        timerSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {

                //set the time limit as user want
                updateTimer(progress)

            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {

            }
        })
    }

    fun updateTimer(secondsLeft:Int){
        var minutes = secondsLeft/60 as Int
        var seconds = secondsLeft - minutes*60

        var secondString = seconds.toString()

        if(seconds<=9){
            secondString = "0"+secondString
        }

        timerTextView.text = minutes.toString() + ":" +secondString
    }

    //when click button go
    fun controlTimer(v: View){
        //if counter active is false means that the timer is not running so when we click a button it means go
        if(!counterIsActive){
            counterIsActive=true
            timerSeekBar.isEnabled = false//no set time now
            controllerButton.text = "Stop"

            countDownTimer = object : CountDownTimer(timerSeekBar.progress.toLong()*1000 + 100, 1000){
                override fun onTick(millisUntilFinished: Long) {
                    updateTimer((millisUntilFinished/1000).toInt())
                }

                override fun onFinish() {
                    //if finished
                    resetTimer()
                    var mPlayer:MediaPlayer = MediaPlayer.create(applicationContext,R.raw.airhorn)
                    mPlayer.start() //honking
                }
            }.start()
        }else{
            //two cases we press stop or timer is ended
            resetTimer()
        }
    }

     fun resetTimer() {
        timerTextView.text="0:30"
        timerSeekBar.progress=30
        countDownTimer.cancel()
        controllerButton.text="Go!"
        timerSeekBar.isEnabled=true
        counterIsActive=false
    }
}

