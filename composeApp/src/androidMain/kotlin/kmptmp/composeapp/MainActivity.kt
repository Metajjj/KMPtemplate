package kmptmp.composeapp

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import kmptmp.composeapp.Theme.ThemeItfc
import org.koin.android.ext.koin.androidContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        PlatDeps().initKoin {
            androidContext(this@MainActivity)
        };

        setContent {
            App2()
        }

        ReqPerms()
    }


    fun ReqPerms() {

        val RFAR = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
            ,
            {}
        );

        listOf(
            if(Build.VERSION.SDK_INT>=33) Manifest.permission.POST_NOTIFICATIONS else "",

        ).forEach {
            if(it.isNotBlank())
                RFAR.launch(it)
        }
    }

}

