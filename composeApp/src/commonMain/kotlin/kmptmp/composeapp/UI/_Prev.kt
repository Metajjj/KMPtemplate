package kmptmp.composeapp.UI

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation3.runtime.rememberNavBackStack
import kmptmp.composeapp.NavRoutesConfig
import kmptmp.composeapp.koinModules
import org.koin.compose.KoinApplicationPreview
import org.koin.dsl.module

@Preview(
    //portrait
    widthDp = 20*16,
    heightDp = 20*9,
) @Composable fun Prev(c: @Composable ()->Unit = {} ){
    val n = rememberNavBackStack( NavRoutesConfig );
    KoinApplicationPreview({
        modules(module{single { n }})
        modules(koinModules())
    }) {
        c.invoke()
    }
}