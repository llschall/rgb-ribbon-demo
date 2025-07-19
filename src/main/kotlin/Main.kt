import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.llschall.ardwloop.ArdwloopStarter

fun getTextToDisplay(): String {
    return "Featuring ardwloop "+
            ArdwloopStarter.VERSION
}

@Composable
@Preview
fun App() {
    val text = getTextToDisplay()
    MaterialTheme {
        Text(text)
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "Compose App") {
        App()
    }
}
