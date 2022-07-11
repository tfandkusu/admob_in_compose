[![codecov](https://codecov.io/gh/tfandkusu/android_app_template/branch/main/graph/badge.svg?token=DQI5AN5H0Q)](https://codecov.io/gh/tfandkusu/android_app_template)

# AdMob in Jetpack Compose

# Functionality

- It displays a list of [tfandkusu](https://github.com/tfandkusu)'s public GitHub repositories.
- Users can like the repository.
- It displays [AdMob anchored adaptive banner ad](https://developers.google.com/admob/android/banner/anchored-adaptive) in the bottom.
- It displays [AdMob native advanced ad](https://developers.google.com/admob/android/native/advanced) in the list. (Infeed ads)

<img src="https://user-images.githubusercontent.com/16898831/178310015-4416e2fc-74fd-44b8-ac2a-fd4309818ed9.png" width="320">

## Technical feature

This app's LazyColumn has a View recycle mechanism for [AndroidVew](https://developer.android.com/jetpack/compose/interop/interop-apis#views-in-compose) like [RecyclerView](https://developer.android.com/guide/topics/ui/layout/recyclerview).

# Install

Current main branch.

[<img src="https://dply.me/qsuy6f/button/large" alt="Try it on your device via DeployGate">](https://dply.me/qsuy6f#install)

# Architecture

The 3 layers described in [Android recommended app architecture](https://developer.android.com/jetpack/guide#recommended-app-arch)

- UI Layer
    - [Jetpack Compose](https://developer.android.com/jetpack/compose)
    - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)
- Domain Layer
- Data Layer
    - Repository
    - Data Sources
        - [Retrofit](https://github.com/square/retrofit)
        - [Room](https://developer.android.com/jetpack/androidx/releases/room)


# Module structure

![image](https://user-images.githubusercontent.com/16898831/154816419-e711ffd2-41ea-45ac-bdde-49424be4f336.png)

Multiple `compose`, `presentation`, and  `usecase`  modules will be created for each feature.

## app

- Activity
- Compose navigation host

## compose

It has minimum dependency to speed up compose preview.

- Compose
- Compose preview
- ViewModel interface
- ViewModel implementation for compose preview

## presentation

- ViewModel implementation for production

## viewCommon

- Common API error handling
- Utility for ViewModel and LiveData

## usecase

- Domain layer

## repository

- Represents the data layer

## localDataStore

- Use room to save data locally.

## remoteDataStore

- Use Retrofit to access REST API.

# Technology used

All libraries used are defined in [lib.versions.toml](https://github.com/tfandkusu/android_app_template/blob/main/gradle/libs.versions.toml)

**Ref:** [The version catalog TOML file format](https://docs.gradle.org/7.0.2/userguide/platforms.html#sub::toml-dependencies-format)

## View layer

- [Jetpack Compose](https://developer.android.com/jetpack/compose)
- [Navigation Compose](https://developer.android.com/jetpack/compose/navigation)
- [Mobile Ads SDK](https://developers.google.com/admob/android/quick-start)
- [RecomposeHighlighter](https://github.com/android/snippets/blob/master/compose/recomposehighlighter/src/main/java/com/example/android/compose/recomposehighlighter/RecomposeHighlighter.kt)

## Presentation layer

- [androidx.compose.runtime:runtime-livedata](https://developer.android.com/jetpack/compose/libraries#streams)

## Data layer

- [Retrofit](https://github.com/square/retrofit)
- [Room](https://developer.android.com/jetpack/androidx/releases/room)

## DI

- [Dagger Hilt](https://developer.android.com/training/dependency-injection/hilt-android)
- [Hilt Navigation Compose](https://developer.android.com/jetpack/compose/libraries#hilt-navigation)

## Unit test

- [MockK](https://github.com/mockk/mockk)
- [Kotest](https://github.com/kotest/kotest)
- [Robolectric](http://robolectric.org/)

## Coverage

- [Jacoco](https://www.eclemma.org/jacoco/)
- [Codecov](https://about.codecov.io/)

## CI/CD

- [GitHub Actions](https://docs.github.com/actions)
- [gradle-build-action](https://github.com/gradle/gradle-build-action)
- [Spotless plugin for Gradle](https://github.com/diffplug/spotless/tree/main/plugin-gradle)
- [Danger](https://danger.systems/ruby/)
- [danger-android_lint](https://github.com/loadsmart/danger-android_lint)

## Other

- [OSS Licenses Gradle Plugin](https://github.com/google/play-services-plugins/tree/master/oss-licenses-plugin)
- [Timber](https://github.com/JakeWharton/timber)

# References

- [UnidirectionalViewModel](https://github.com/DroidKaigi/conference-app-2021/blob/main/uicomponent-compose/core/src/main/java/io/github/droidkaigi/feeder/core/UnidirectionalViewModel.kt) from [DroidKaigi/conference-app-2021](https://github.com/DroidKaigi/conference-app-2021)
