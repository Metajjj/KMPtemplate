@file:OptIn(ExperimentalTestApi::class, ExperimentalComposeUiApi::class)

package multi.remindful.note


import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.backhandler.BackHandler
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.runComposeUiTest
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.savedstate.read
import kmptmp.composeapp.NavVM
import kmptmp.composeapp.PlatDeps
import kmptmp.composeapp.RemindFulVM
import kmptmp.composeapp.UI.NavRoutes
import kmptmp.composeapp.UI.NoteEdi
import kotlinx.coroutines.runBlocking
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.get
import kotlin.test.AfterTest
import kotlin.test.BeforeTest


//Default params if not testing activity-related
open class TestWrapper() : KoinTest {

    protected lateinit var VMDB : RemindFulVM

    protected lateinit var NavVm : NavVM;

    //Force screen to wake up if off!
    @BeforeTest open fun setUp() {
        UISetup()

        //TODO runs off plat-specific DB not mem vers!

        //Cannot do mem vers? Just wipe?


        VMDB = get();//Nav=get()

        NavVm = get<NavVM>()


        //TODO currNotes always 0 size?!?
        //println("Del All Notes")
        runBlocking {

            val varargs = (0L..100).toList()

            //println("Del: $varargs")
            VMDB.DelNotes(* varargs.toLongArray() )
        }

    }

    fun UISetup() = runComposeUiTest {
        setContent {

            val n = rememberNavController();

            PlatDeps().initKoin{
                modules(module{single{n}})
            }

            //TODO ERR lifecycleOwner not present
            return@setContent
            NavHost(
                navController = n,
                startDestination = NavRoutes.Landing.name,

                builder= {

                    composable(NavRoutes.Landing.name) {}


                    //NOTE EDIT
                    composable(
                        "${NavRoutes.NoteEditor.name}/{id}",

                        arguments = listOf(
                            navArgument("id", { type = NavType.LongType; defaultValue = -1L }),
                        ),

                        content = {

                            //KMP - need to `read` the args
                            it.arguments!!.read {
                                val nI = getLong("id");

                                //Cntrl back button if left on Note and noti remind launches it again i.e. onIntentChange
                                BackHandler {
                                    n.navigate(NavRoutes.Home.name) {
                                        popUpTo("${NavRoutes.NERemind.name}/{$nI}") { inclusive = true }
                                    }
                                }


                                //rerun if ID changes (redundant)
                                val note = runBlocking { VMDB.NbyID(nI) }


                                //Read 3 times instead of 1?
                                println("ni: $nI | $note")
                                //note should never be null
                                //TODO fails for noteEdi

                                NoteEdi(note.firstOrNull())
                            }

                        }
                    )

                }
            )
        }
    }

    @AfterTest open fun tearDown() {

    }

}

//===NOTE : Most likely app/data is wiped before tests - dont test on actual devices ====