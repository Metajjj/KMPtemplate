package kmptmp.composeapp.Theme

import androidx.compose.runtime.mutableStateOf
import kmptmp.composeapp.PlatDeps
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.runBlocking
import kotlinx.io.buffered
import kotlinx.io.files.Path
import kotlinx.io.files.SystemFileSystem
import kotlinx.io.readByteArray
import kotlinx.serialization.json.Json
import kmptmp.composeapp.Theme.myThemes.BlueTheme
import kmptmp.composeapp.Theme.myThemes.MainTheme
import kmptmp.composeapp.Theme.myThemes.myColors
import kmptmp.composeapp.generated.resources.Res
import kmptmp.composeapp.generated.resources.app_name
import org.jetbrains.compose.resources.getString


class ThemeItfc {

    //pseudo static
    companion object{

        //nullify for previews
        private val TmpFile by lazy{
            try{
            Path(
                PlatDeps().AppDir,
                runBlocking { getString(Res.string.app_name) } + "_theme"
            );
            } catch(e:Exception){println("prob preview"); null}
        }

        private val ThemesMap =
            mapOf(
                "MainTheme" to MainTheme,

                "BlueTheme" to BlueTheme,
            )


        enum class ThemeType{ INDEX, NAME, COLORS }

        //getter so cant set
        /**
         * Purely for use in compose to access theme in object syntax
         */
        val cTheme
            get() = currTheme.value.value

        //using lazy/run as once to delegate setup to when its accessed and avoids needing to explicitly call setup
        /**
         * current theme information (source truth)
         */
        private var currTheme = run {
            SetupFile()
            mutableStateOf(ThemesMap.firstNotNullOf { it })
        }


        //=============

        //Methods to interact with ThemeInterface

        /**
         * [ThemeType.INDEX] -> int
         * [ThemeType.NAME] -> key/string
         * [ThemeType.COLORS] -> value/json of [myColors]
         */
        fun getTheme(t: ThemeType): String = when(t){
            ThemeType.INDEX -> ThemesMap.keys.indexOf(currTheme.value.key).toString()
            ThemeType.NAME -> currTheme.value.key
            ThemeType.COLORS -> Json.encodeToString(currTheme.value.value)
        }

        /**
         * [b] true (default) = +1 | false = -1
         */
        fun themeUpdate(b: Boolean =true) = themeUpdate(
            getTheme(ThemeType.INDEX).toInt() + if(b) 1 else -1
        )
        /**
         * public/compose via themeUpdate(bool)
         */
        private fun themeUpdate(i:Int) : Boolean =
            try {
                //first index match -- errors throws into catch
                currTheme.value = ThemesMap.entries.first {
                    ThemesMap.keys.indexOf(it.key) == when(true) {
                        (i < 0) -> ThemesMap.size - 1
                        (i >= ThemesMap.size) -> 0
                        else -> i
                    }
                }

                //write if success
                TmpFile?.let{
                SystemFileSystem.sink(it).buffered()
                    .use {
                        it.write(
                            getTheme(ThemeType.INDEX).encodeToByteArray()
                        )
                    }
                }

                true
            } catch (e: Exception){ //Preview errs if changing theme
                println(e)

                false
            }



        //===============

        //Core of ThemeInterface

        //private so never called outside our intended usage
        private fun SetupFile(){

            //shadow antinull
            val TmpFile = TmpFile ?: return;

            if (! SystemFileSystem.exists(TmpFile)) {

                SystemFileSystem.createDirectories(TmpFile.parent!!)

                SystemFileSystem.sink(TmpFile).buffered()
                    .use {
                        it.write(
                        getTheme(ThemeType.INDEX).encodeToByteArray()
                    )
                }
            }


            try {
                //Can only read once!!
                if (!themeUpdate( SystemFileSystem.source(TmpFile).buffered().readByteArray().decodeToString().toInt()
                ))
                    SystemFileSystem.delete(TmpFile)

            } catch (e: Exception) {
                println(e); SystemFileSystem.delete(TmpFile)
            }
        }
    }
}

