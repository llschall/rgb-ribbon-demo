package org.llschall.rgbstrip.controller

import org.llschall.rgbstrip.model.AppModel
import org.llschall.ardwloop.ArdwloopStarter
import org.llschall.rgbstrip.model.ArdwloopProgram

class AppController(private val model: AppModel) {
    fun loadVersion() {
        model.version = ArdwloopStarter.VERSION
    }

    fun start() {
        ArdwloopStarter.get().start(ArdwloopProgram(), 9600)
    }

}

