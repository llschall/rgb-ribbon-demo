package org.llschall.ribbon.controller

import org.llschall.ribbon.model.AppModel
import org.llschall.ardwloop.ArdwloopStarter
import org.llschall.ribbon.model.ArdwProgram

class AppController(private val model: AppModel) {
    fun loadVersion() {
        model.version = ArdwloopStarter.VERSION
    }

    fun start() {
        ArdwloopStarter.get().start(ArdwProgram(), 9600)
    }

}

