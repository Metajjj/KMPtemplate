import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {

    //Need this so upgrade assisstant works
    //id("com.android.application") version "9.1.0"

    listOf(

        //libs.plugins.androidLibrary,
        //libs.plugins.androidApplication,
        libs.plugins.newKmpLib, //new kmp lib handles androidLibrary/Application usages

        libs.plugins.composeHotReload,
        libs.plugins.composeMultiplatform,
        libs.plugins.composeCompiler,
        libs.plugins.kotlinMultiplatform, //platform-specific code
        //libs.plugins.newKmpLib, //to make commonMain compile to diff targets

        libs.plugins.dev.ksp,

        libs.plugins.mokkery,

        //serialiser
        libs.plugins.serialize,


        //rust compatibility --- temp not working for latest KMP + AGP 9
        //gobley fun
//        libs.plugins.gobley.atomicfu, //for thread-safe/atomic (foundation)
//        libs.plugins.gobley.cargo, //build + link process (wiring)
//        libs.plugins.gobley.uniffi, //kotlin <=> rust (action)

    ).forEach { alias(it) }
}

//adjust to grab 'package/directories' auto?
val pkgName = "kmptmp.composeapp"

kotlin {

    android {


        namespace = pkgName
        compileSdk = libs.versions.android.compileSdk.get().toInt()
        minSdk = libs.versions.android.minSdk.get().toInt()

        //for the R / resources accessible
        androidResources { enable=true }

        //modify android to enable preAPI usage of API stuff
        //compilerOptions{ enableCoreLibraryDesugaring=true }
    }

    listOf(
        iosArm64(),
        iosSimulatorArm64(),
        //iosX64(), //breaks??
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = pkgName.substring(pkgName.lastIndexOf('.'))
            isStatic = true
        }
    }

    jvm()

    /*Web doesn't support RoomDB fn
    js { browser();binaries.executable() }
    @OptIn(ExperimentalWasmDsl::class)
    wasmJs { browser(); binaries.executable() }*/

    sourceSets {
        commonMain.dependencies {
            listOf(

                //Compose
                libs.compose.runtime,
                libs.compose.foundation,
                libs.compose.material3,
                libs.compose.ui,
                //libs.compose.ui.tool, //androidMain (uses android virt device)
                libs.compose.ui.toolPrev,
                libs.compose.components.resources,

                //tmp
                libs.compose.constraint,

                //serialise
                libs.serializable.json,

                //nav ??
                libs.compose.nav,
                libs.compose.nav.vm,
                //libs.compose.nav.backhandler,

                //koin
                project.dependencies.platform(libs.bom.koin),
                libs.koin.core,
                libs.koin.compose,
                libs.koin.composevm,

                //roomdb
                libs.room.runtime,
                libs.sqlite.bundled,

                //kotlinx
                libs.kotlinx.datetime,
                libs.kotlinx.io.core,

                ).forEach{
                implementation(it)
            }
        }
        commonTest.dependencies {
            listOf(
                libs.kotlin.test,
                libs.compose.nav,

                libs.compose.ui.test,

                libs.room.runtime, //For test vers

                libs.junit,

                project.dependencies.platform(libs.bom.koin),
                libs.koin.test,
                //libs.test.junit.ext,
                //libs.kotlin.junit.test,
                //libs.compose.ui.test.junit4,

            ).forEach{ implementation(it) }
        }


        androidMain.dependencies {
            listOf(
                project.dependencies.platform(libs.bom.koin),
                libs.koin.android,

                libs.compose.ui.tool, //preview (uses android virt device)

            ).forEach{ implementation(it) }

        }

        jvmMain.dependencies {
            listOf(
                compose.desktop.currentOs,
                libs.kotlinx.coroutinesSwing,
            ).forEach{ implementation(it) }
        }
    }
}

dependencies {
    //for compile stuff
    //ksp (annotation-using libraries only)
    listOf(
        //compiler / annotators
        libs.room.compiler,
    ).forEach {
        //ksp(it) - not allowed for multi plat

        //return@forEach; //Figure out later
        add("kspAndroid", it)
        add("kspJvm", it)

        return@forEach
        add("kspIosX64", it)
        add("kspIosArm64",it)
        add("kspIosSimulatorArm64",it)

        //add("kspJs", it);add("kspWasmJs", it)
    }
}

compose.desktop {
    application {
        mainClass = pkgName + "MainKt"


        nativeDistributions {

            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb, TargetFormat.AppImage, TargetFormat.Exe, TargetFormat.Rpm, TargetFormat.Pkg,)
            packageName = pkgName.substring(pkgName.lastIndexOf('.'))
            packageVersion = "1.0.0"

            //Project.File() starts from project/composeApp/
            val icon = "./src/commonMain/composeResources/files/RemindFulIcon2.png"
            //Linux{ iconFile.set(project.file(icon)) }
        }
    }
}



/*
//Rust
cargo{

    packageDirectory = layout.projectDirectory.dir("./src/commonMain/rust/myRustDir")

    //jvm/desktop target filter
    */
/*builds.jvm{
        embedRustLibrary = when (rustTarget) {

            GobleyHost.current.rustTarget,
            RustPosixTarget.LinuxX64,
            RustPosixTarget.MinGWX64, //windows via gnu
                -> true
            else -> false
        }
    }*//*


}
*/
