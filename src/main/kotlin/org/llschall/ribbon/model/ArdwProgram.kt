package org.llschall.ribbon.model

import org.llschall.ardwloop.IArdwProgram
import org.llschall.ardwloop.value.SerialData
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicInteger

/**
 * Represents an ardwloop program with a name and code.
 */
class ArdwProgram : IArdwProgram {

    var buildInLed = AtomicBoolean(false)

    var red = AtomicInteger()
    var green = AtomicInteger()
    var blue = AtomicInteger()

    // You can add additional properties or methods here if needed
    override fun ardwSetup(p0: SerialData?): SerialData? {
        return SerialData()
    }

    override fun ardwLoop(p0: SerialData?): SerialData? {

        val data = SerialData()
        if (buildInLed.get())
            data.a.v = 1;
        else
            data.a.v = 0;

        data.a.x = red.get()
        data.a.y = green.get()
        data.a.z = blue.get()

        return data
    }
}
