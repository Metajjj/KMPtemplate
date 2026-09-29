package kmptmp.composeapp

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import kmptmp.composeapp.UI.CHome
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

@OptIn(ExperimentalComposeUiApi::class)
@Composable fun App2(){


        //For the back-press handler!
            //rememberNavHost()
        val navy = rememberNavBackStack(
            NavRoutesConfig,
            NavRoutes.Home //default/init
        )

        loadKoinModules(module{single<NavBackStack<NavKey>>{navy}})


        NavDisplay(
            backStack = navy,

            /*//Add to stack
            transitionSpec = {iAnim togetherWith oAnim},

            //remove from stack (back button)
            popTransitionSpec = {iAnim togetherWith oAnim},

            //swipe gesture (follows finger mid-gesture to animate timeline)
            predictivePopTransitionSpec = {iAnim togetherWith oAnim},*/


            onBack = {
                //overarching back handler
                navy.removeLastOrNull()
            },

            entryProvider = entryProvider{

                entry<NavRoutes.Home>{
                    CHome(

                    );
                }

                entry<NavRoutes.Call>{

                    //it is the class : Navroute : Key object
                    /*CCall(
                        it.ct
                    )*/

                }
            }
        )

}