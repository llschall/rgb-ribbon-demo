package org.llschall.ribbon.controller

import org.llschall.ribbon.model.AppModel
import org.llschall.ardwloop.ArdwloopStarter
import org.llschall.ribbon.model.ArdwProgram

class AppController(private val model: AppModel) {
    fun loadVersion() {
        model.version = ArdwloopStarter.VERSION
    }

    fun start() {
        val program = ArdwProgram()
        model.ardwProgram = program
        ArdwloopStarter.get().start(program, 9600)
    }

    fun toggleLed13() {
        model.ardwProgram?.let {
            it.led13.set(!it.led13.get())
        }
    }

}
