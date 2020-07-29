package com.sanath.decisionmaker

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.os.Vibrator
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.*
import kotlin.math.sqrt

class MainActivity : AppCompatActivity() {

//    companion object {
//        private const val REQUEST_CODE_STT = 1
//    }

//    lateinit var voiceInput: Button
    lateinit var resultBox: TextView

    private var sensorManager: SensorManager? = null
    private var acceleration = 0f
    private var currentAcceleration = 0f
    private var lastAcceleration = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        Objects.requireNonNull(sensorManager)!!.registerListener(sensorListener, sensorManager!!
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL)
        acceleration = 10f
        currentAcceleration = SensorManager.GRAVITY_EARTH
        lastAcceleration = SensorManager.GRAVITY_EARTH

//        voiceInput = findViewById(R.id.btn_voice_input)

//        voiceInput.setOnClickListener {
//            val sttIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
//            sttIntent.putExtra(
//                    RecognizerIntent.EXTRA_LANGUAGE_MODEL,
//                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
//            )
//            sttIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
//            sttIntent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak now!")
//
//            try {
//                startActivityForResult(sttIntent, REQUEST_CODE_STT)
//            } catch (e: ActivityNotFoundException) {
//                e.printStackTrace()
//                Toast.makeText(this, "Your device does not support STT.", Toast.LENGTH_LONG).show()
//            }
//        }

    }

    private val sensorListener: SensorEventListener = object : SensorEventListener {
        override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
        }

        override fun onSensorChanged(event: SensorEvent) {
            val x = event.values[0]
            val y = event.values[1]
            val z = event.values[2]
            lastAcceleration = currentAcceleration
            currentAcceleration = sqrt((x * x + y * y + z * z).toDouble()).toFloat()
            val delta: Float = currentAcceleration - lastAcceleration
            acceleration = acceleration * 0.9f + delta
            if (acceleration > 40) {
                val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                Toast.makeText(applicationContext, "Shake event detected", Toast.LENGTH_SHORT).show()
                vibrator.vibrate(200)

                val positiveResponse = arrayOf("As I see it, yes", "It is certain", "It is decidedly so", "Most likely", "Outlook good", "Signs point to yes", "Without a doubt", "Yes", "Yes-definitely", "You may rely on it")
                val negativeResponse = arrayOf("Ask again later", "Better not tell you now", "Cannot predict now", "Concentrate and ask again", "Don't count on it", "My reply is no", "My sources say no", "Outlook not so good", "Reply hazy, try again", "Very doubtful")

                resultBox = findViewById(R.id.tv_result)

                val positiveOrNegative = (0..1).random();

                if(positiveOrNegative == 1) {
                    resultBox.setText(positiveResponse[(0..9).random()])
                }
                else {
                    resultBox.setText(negativeResponse[(0..9).random()])
                }

//                val sttIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
//                sttIntent.putExtra(
//                        RecognizerIntent.EXTRA_LANGUAGE_MODEL,
//                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
//                )
//                sttIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
//                sttIntent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak now!")
//
//                try {
//                    startActivityForResult(sttIntent, REQUEST_CODE_STT)
//                } catch (e: ActivityNotFoundException) {
//                    e.printStackTrace()
////                    Toast.makeText(this, "Your device does not support STT.", Toast.LENGTH_LONG).show()
//                }
            }
        }
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        resultBox = findViewById(R.id.et_text_input)
//
//        when (requestCode) {
//            REQUEST_CODE_STT -> {
//                if (resultCode == Activity.RESULT_OK && data != null) {
//                    val result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
//                    result?.let {
//                        val recognizedText = it[0]
//                        resultBox.setText(recognizedText)
//                    }
//                }
//            }
//        }
//    }
}