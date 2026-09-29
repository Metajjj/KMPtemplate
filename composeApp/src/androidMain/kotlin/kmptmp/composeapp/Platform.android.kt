package kmptmp.composeapp

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

import kmptmp.composeapp.RoomDB.RemindFulDB
import org.koin.core.context.GlobalContext

actual fun PlatDeps() = object : IPlatDep  {

    //context-based stuff should always be lazy
    override val AppDir by lazy {
        GlobalContext.get().get<Context>().applicationContext.cacheDir.absolutePath
    }

    override fun getDbB(): RoomDatabase.Builder<RemindFulDB> =
        Room
            .databaseBuilder(
                GlobalContext.get().get<Context>() , name="$AppDir/${RemindFulDB::class.simpleName}"
            )

}
