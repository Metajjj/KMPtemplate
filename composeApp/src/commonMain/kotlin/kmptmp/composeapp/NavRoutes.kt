package kmptmp.composeapp

import androidx.navigation3.runtime.NavKey
import androidx.savedstate.serialization.SavedStateConfiguration
import kmptmp.composeapp.NavRoutes.Call
import kotlinx.serialization.Serializable
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic


@Serializable
sealed interface NavRoutes : NavKey
{
    //object - no need params
    @Serializable data object Home : NavRoutes

    @Serializable data class Call (val ct : callType) : NavRoutes
}
@Serializable
enum class callType{
    Normal,
    Staff,
    Alert
}


//Tell it how to restore/backtrack
val NavRoutesConfig
    get() = SavedStateConfiguration {
        //Uses serializer module to do so
        serializersModule = SerializersModule {
            //NavKey ancestor/super/common class
            polymorphic(NavKey::class){
                subclass(NavRoutes.Home::class, NavRoutes.Home.serializer())

                subclass(Call::class, Call.serializer())
            }
        }
    }
