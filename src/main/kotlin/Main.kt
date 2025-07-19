import org.llschall.ledstrip.model.AppModel
import org.llschall.ledstrip.controller.AppController
import org.llschall.ledstrip.view.AppView

fun main() {
    val model = AppModel()
    val controller = AppController(model)
    controller.loadVersion()
    val view = AppView(model, controller)
    view.show()
}
