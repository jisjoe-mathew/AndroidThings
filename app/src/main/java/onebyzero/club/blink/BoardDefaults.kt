package onebyzero.club.blink

import android.os.Build

object BoardDefaults {
    private const val DEVICE_RPI3 = "rpi3"


    val gpioForLED = when (Build.DEVICE) {
        DEVICE_RPI3 -> "BCM13"
        else -> throw IllegalStateException("Unknown Build.DEVICE ${Build.DEVICE}")
    }
}