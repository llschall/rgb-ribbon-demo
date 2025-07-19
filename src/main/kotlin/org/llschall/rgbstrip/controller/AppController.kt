package org.llschall.rgbstrip.controller

import org.llschall.rgbstrip.model.AppModel
import org.llschall.ardwloop.ArdwloopStarter

class AppController(private val model: AppModel) {
    fun loadVersion() {
        model.version = ArdwloopStarter.VERSION
    }
}

