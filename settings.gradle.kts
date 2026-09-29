//affects project name  too!

rootProject.name = Regex("[^_!-:\\/]+").find(
    System.getProperty("user.dir").substringAfterLast(File.separator)
    ,
)?.value ?: "ProjNameErr"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        mavenCentral()
        google()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
        google()
        gradlePluginPortal()
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

include(":composeApp")

include(":androidApp")
