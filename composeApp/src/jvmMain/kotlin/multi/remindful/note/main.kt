package multi.remindful.note

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import kmptmp.composeapp.App2
import kmptmp.composeapp.PlatDeps
import kmptmp.composeapp.Theme.ThemeItfc

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "note",
    ) {
        //App()
        PlatDeps().initKoin()
        ThemeItfc.SetupFile()

        App2();
    }
}