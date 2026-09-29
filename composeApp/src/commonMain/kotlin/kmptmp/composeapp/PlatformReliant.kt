package kmptmp.composeapp

import androidx.lifecycle.ViewModel
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kmptmp.composeapp.RoomDB.NotesDAOs
import kmptmp.composeapp.RoomDB.RemindFulDB
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module
import org.koin.mp.KoinPlatform.stopKoin

interface IPlatDep {

    val AppDir : String;

    //RoomDB based on platform DbB - pass to Koin module
    fun getDbB(): RoomDatabase.Builder<RemindFulDB>


    fun initKoin(config : KoinAppDeclaration ?= null){

        stopKoin();
        startKoin {

            config?.invoke(this)

            modules(
                koinModules()
            )
        }
    }

}

expect fun PlatDeps(): IPlatDep

fun koinModules() = mutableListOf(
    //DB
    /*ModuleDb,
    ModuleDao,
    ModuleRVm,*/

    //ModuleNav,
    ModuleNavVm,
)



// KOIN

val ModuleDb = module {
    single<RemindFulDB>{
        PlatDeps().getDbB()
            .setDriver(BundledSQLiteDriver())
            .setQueryCoroutineContext(Dispatchers.IO)
            .build()
    }
}


val ModuleDao = module {
    single<NotesDAOs>{get<RemindFulDB>().NotesDao()}
}

//  DB ViewModel
//ViewModel = interaction/service between UI & DB i.e. use these user-friendly functions to pull on DAO operations

class RemindFulVM (
    private val NotesDao: NotesDAOs
) : ViewModel()
{}
val ModuleRVm = module { viewModelOf(::RemindFulVM) }


//  NAV CNTRL
class NavVM (val n : NavBackStack<NavKey>) : ViewModel(){
    fun navigate(dest: NavRoutes) {
        if(n.last() == dest){ n.add(n.removeLast()) }
        else { n.add(dest) }
    }
}
val ModuleNavVm = module { viewModelOf(::NavVM) }