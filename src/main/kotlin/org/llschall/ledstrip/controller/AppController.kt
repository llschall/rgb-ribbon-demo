package org.llschall.ledstrip.controller

import org.llschall.ledstrip.model.AppModel
import org.llschall.ardwloop.ArdwloopStarter
import org.llschall.ledstrip.model.ArdwProgram

class AppController(private val model: AppModel) {
    fun loadVersion() {
        model.version = ArdwloopStarter.VERSION
    }

    fun start() {
        ArdwloopStarter.get().start(ArdwProgram(), 9600)
    }

}

