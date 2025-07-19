package org.llschall.rgbstrip.model

import org.llschall.ardwloop.IArdwProgram
import org.llschall.ardwloop.value.SerialData

/**
 * Represents an ardwloop program with a name and code.
 */
class ArdwProgram : IArdwProgram {
    // You can add additional properties or methods here if needed
    override fun ardwSetup(p0: SerialData?): SerialData? {
        return SerialData()
    }

    override fun ardwLoop(p0: SerialData?): SerialData? {
        return SerialData()
    }
}
