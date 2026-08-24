# Status: Active
This repository is actively maintained.

![status: active](https://img.shields.io/badge/status-active-brightgreen.svg)

This is an independent, unofficial community-maintained fork of the
[original Google Sceneform repository](https://github.com/google-ar/sceneform-android-sdk),
which was archived by Google in 2020.

This project is not affiliated with, sponsored by, or endorsed by Google.

Sceneform SDK for Android
=========================
Copyright (c) 2018 Google Inc.  All rights reserved.

Modifications Copyright (c) 2026 Mukesh Kumar and contributors.

Sceneform is a 3D framework with a physically based renderer that's optimized
for mobile devices and makes it easy for you to build augmented reality
applications without requiring OpenGL.


## Choosing the right Sceneform SDK version for your project

For the original Sceneform releases 1.0.0–1.17.1, see the archived
[Google Sceneform repository](https://github.com/google-ar/sceneform-android-sdk).

For an updated version supporting modern Android development, use Sceneform
2.0.0 from this unofficial community-maintained repository.

## What's new and changed in Sceneform 2.0.0


### Main changes

* Merged `sceneformsrc` and `sceneformux` into the single `sceneform-nxt` module.
* Added Kotlin source compilation and coroutine support.
* Migrated the updated SDK and sample to AndroidX.
* Added compatibility with Android API 36 and Java 17.
* Updated ARCore and Filament integrations.
* Added HDR and KTX environment-loading utilities.
* Added asynchronous model and resource loading.
* Updated glTF animation handling for Filament 1.70.1.
* Modernized Gradle configuration and module namespaces.
* Updated the glTF sample for the new module and APIs.

### New features

* Added a video node with rounded-corner support.

### Updated toolchain

* Android Gradle Plugin: `8.10.1`
* Gradle: `8.11.1`
* Kotlin: `2.1.20`
* Java: `17`
* Compile SDK: `36`
* Target SDK: `36`
* Minimum SDK: `24`

### Updated core libraries

* Google ARCore: `1.51.0`
* Google Filament: `1.70.1`
* Filament GLTFIO: `1.70.1`
* Filament Utils: `1.70.1`
* Filamat: `1.70.1`

### AndroidX and Kotlin libraries

* AndroidX AppCompat: `1.7.0`
* AndroidX Activity: `1.10.1`
* AndroidX Fragment: `1.8.6`
* AndroidX Lifecycle Runtime KTX: `2.8.7`
* Kotlin Coroutines Android: `1.9.0`
* Kotlin Coroutines JDK 8: `1.9.0`

### Resource loading

* Fuel: `2.3.1`
* Fuel Coroutines: `2.3.1`


## Getting started with Sceneform 2.0.0

Use the following steps to include and build the Sceneform 2.0.0 SDK with your
app:

1. Download `sceneform-android-sdk-2.0.0.zip` from the Sceneform SDK
   [releases](https://github.com/MukeshKumar009/sceneform-android-sdk/releases/tag/v2.0.0)
   page.
2. Extract the `sceneform-nxt` directory into your project's
   top-level directory. The resulting directory structure should be similar to
   the following:
```
project
+-- app
|   +-- build.gradle
|   +-- ...
+-- sceneform-nxt
|   +-- sceneform
|       +-- build.gradle
|       +-- src
|       +-- ...
+-- build.gradle
+-- settings.gradle
+-- ...
```

3. Modify your project's `settings.gradle` to include the Sceneform project:
```
include ':app'

// Add these lines:
include ':sceneform-nxt'
project(':sceneform-nxt').projectDir=new File('sceneform-nxt/sceneform')
```

4. Finally, add a reference to the Sceneform SDK to your app's `build.gradle`:
```
dependencies {
    api project(":sceneform-nxt")
}
```

To get started with the Sceneform SDK, check out the
[Sceneform sample](https://github.com/MukeshKumar009/sceneform-android-sdk/tree/master/samples/gltf/app).


## Archived Sceneform 1.15.0 content

Documentation for the Sceneform SDK for Android 1.15.0 is available from
https://developers.google.com/sceneform.

* [Getting started](https://developers.google.com/sceneform/develop/getting-started)
* [API reference](https://developers.google.com/sceneform/reference)
* [Samples](https://github.com/google-ar/sceneform-android-sdk/tree/v1.15.0/samples)


## Release notes

The SDK release notes are available on the
[releases](https://github.com/MukeshKumar009/sceneform-android-sdk/releases) page.


## Trademark and attribution

Sceneform is a trademark of Google LLC. This repository is an independent,
unofficial fork of Google's archived Sceneform project and is not affiliated
with, sponsored by, or endorsed by Google.

The original Sceneform source code and its copyright notices remain attributed
to Google and the original contributors. Changes introduced in version 2.0.0
are maintained independently by this repository's contributors.


## License

Please see the
[LICENSE](LICENSE)
file.
