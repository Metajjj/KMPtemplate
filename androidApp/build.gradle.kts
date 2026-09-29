//Android module for 'wrapping' + 'packaging' the app - still relies on KMP for its logic

plugins {

    listOf(
        libs.plugins.androidApplication,
    ).forEach { alias(it) }

}

android {
    namespace = "crummyapp.androidwrapper"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = namespace

        minSdk = libs.versions.android.minSdk.get().toInt()

        versionCode=1

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11

        //modify android to enable preAPI usage of API stuff i.e. java.time in 26 for 23
        isCoreLibraryDesugaringEnabled=true
    }
}

dependencies{
    //must enable in android module since its android specific
    //here to affect how gradle compiles stuff -- sourcets to affect whats visible --- this is needed to make api 26 visible to pre26
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.1.5")

    //Reference other modules via how they are declared in settings.gradle (i.e. colon followed by module folder name)
    implementation(project(":composeApp"))
}