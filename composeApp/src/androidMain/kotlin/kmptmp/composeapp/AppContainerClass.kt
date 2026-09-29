package kmptmp.composeapp

import android.app.Application
import android.media.MediaPlayer
import org.koin.android.ext.android.get
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.logger.Level


class AppContainerClass : Application() {
    //Option to pass states and data and libraries here instead of re-declaring over and over in activities

    override fun onCreate() {
        super.onCreate()

        PlatDeps().initKoin({
            androidContext(applicationContext)
            androidLogger(Level.ERROR)
        })
    }

    override fun onTerminate() {
        super.onTerminate()

        //Ensure its released regardless of activity
        get<MediaPlayer>().also {
            it.stop(); it.release()
        }
    }
}