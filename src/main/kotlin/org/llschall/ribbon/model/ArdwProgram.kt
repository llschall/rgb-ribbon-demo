package org.llschall.ribbon.model

import org.llschall.ardwloop.IArdwProgram
import org.llschall.ardwloop.value.SerialData
import java.util.concurrent.atomic.AtomicBoolean

/**
 * Represents an ardwloop program with a name and code.
 */
class ArdwProgram : IArdwProgram {

    var led13 = AtomicBoolean(false)

    // You can add additional properties or methods here if needed
    override fun ardwSetup(p0: SerialData?): SerialData? {
        return SerialData()
    }

    override fun ardwLoop(p0: SerialData?): SerialData? {

        val data = SerialData()
        if (led13.get())
            data.a.v = 1;
        else
            data.a.v = 0;
        return data
    }
}
