package onebyzero.club.blink

import android.app.Activity
import com.google.android.things.pio.Gpio
import com.google.android.things.pio.PeripheralManager
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Switch
import android.widget.Toast

class MainActivity : Activity() {
    private val handler = Handler()
    private lateinit var ledGpio: Gpio
    private var ledState = false

    private val blinkRunnable = object : Runnable {
        override fun run() {
            // Toggle the GPIO state
            ledState = !ledState
            ledGpio.value = ledState
            Log.d(TAG, "State set to ${ledState}")

            // Reschedule the same runnable in {#INTERVAL_BETWEEN_BLINKS_MS} milliseconds
            handler.postDelayed(this, INTERVAL_BETWEEN_BLINKS_MS)

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "Starting MainActivity")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ledGpio = PeripheralManager.getInstance().openGpio(BoardDefaults.gpioForLED)
        ledGpio.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW)
        Log.i(TAG, "Start blinking LED GPIO pin")
        // Post a Runnable that continuously switch the state of the GPIO, blinking the
        // corresponding LED
        handler.post(blinkRunnable)

    }

    override fun onDestroy() {
        super.onDestroy()
        // Remove pending blink Runnable from the handler.
        handler.removeCallbacks(blinkRunnable)
        // Close the Gpio pin.
        Log.i(TAG, "Closing LED GPIO pin")
        ledGpio.close()
    }

    companion object {
        private val TAG = MainActivity::class.simpleName
        private const val INTERVAL_BETWEEN_BLINKS_MS = 100L
    }
}