package com.google.ar.sceneform.sceneformext.environment

import android.content.Context
import androidx.lifecycle.LifecycleCoroutineScope
import com.google.android.filament.IndirectLight
import com.google.android.filament.Skybox
import com.google.android.filament.utils.KTX1Loader
import com.google.ar.sceneform.sceneformext.Filament
import com.google.ar.sceneform.sceneformext.util.fileBuffer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.nio.Buffer

class KTXEnvironment(
    indirectLight: IndirectLight?,
    sphericalHarmonics: FloatArray? = null,
    skybox: Skybox? = null
) : Environment(
    indirectLight = indirectLight,
    sphericalHarmonics = sphericalHarmonics,
    skybox = skybox
)

/**
 * ### Utility for producing environment resources from precompiled cmgen generated KTX files
 *
 * [Documentation][KTX1Loader.createEnvironment]
 *
 * @param iblKtxFileLocation the ibl file location
 * [Documentation][com.google.ar.sceneform.util.ResourceLoader.fileBuffer]
 * @param skyboxKtxFileLocation the skybox file location
 * [Documentation][com.google.ar.sceneform.util.ResourceLoader.fileBuffer]
 *
 * @return [Documentation][KTX1Loader.createEnvironment]
 */
@JvmOverloads
suspend fun KTX1Loader.loadEnvironment(
    context: Context,
    iblKtxFileLocation: String,
    skyboxKtxFileLocation: String? = null
): Environment? {
    var environment: Environment? = null
    return try {
        val ibl = context.fileBuffer(iblKtxFileLocation)
        val skybox = skyboxKtxFileLocation?.let { context.fileBuffer(it) }
        withContext(Dispatchers.Main) {
            createEnvironment(ibl, skybox)
                .also { environment = it }
        }
    } finally {
        // TODO: See why the finally is called before the onDestroy()
//        environment?.destroy()
    }
}

/**
 * ### Utility for producing environment resources from precompiled cmgen generated KTX files
 *
 * For Java compatibility usage.
 *
 * Kotlin developers should use [KTX1Loader.loadEnvironment]
 *
 * [Documentation][KTX1Loader.loadEnvironment]
 *
 */
@JvmOverloads
fun KTX1Loader.loadEnvironmentAsync(
    context: Context,
    iblKtxFileLocation: String,
    skyboxKtxFileLocation: String? = null,
    coroutineScope: LifecycleCoroutineScope,
    result: (Environment?) -> Unit
) = coroutineScope.launchWhenCreated {
    result(loadEnvironment(context, iblKtxFileLocation, skyboxKtxFileLocation))
}

/**
 * ### Utility for producing environment resources from precompiled cmgen generated KTX files
 *
 * Consumes the content of KTX files and produces an [IndirectLight], SphericalHarmonics and a
 * [Skybox]
 *
 * You can generate ktx ibl and skybox files using:
 *
 * `cmgen --deploy ./output --format=ktx --size=256 --extract-blur=0.1 environment.hdr`
 *
 * Documentation: [Filament - Bake environment map](https://github.com/google/filament/blob/main/web/docs/tutorial_redball.md#bake-environment-map)
 *
 * @param iblKtxBuffer The content of the ibl KTX File.
 * @param skyboxKtxBuffer The content of the skybox KTX File.
 *
 * @return the generated environment indirect light, sphericalHarmonics and skybox from the ktxs.
 *
 * @see KTX1Loader.createIndirectLight
 * @see KTX1Loader.getSphericalHarmonics
 * @see KTX1Loader.createSkybox
 */
//@JvmOverloads
//fun KTX1Loader.createEnvironment(
//    iblKtxBuffer: Buffer?,
//    skyboxKtxBuffer: Buffer? = null
//): KTXEnvironment {
//    // 1. createIndirectLight returns an IndirectLightBundle at runtime.
//    val iblBundle = iblKtxBuffer?.let {
//        it.rewind()
//        KTX1Loader.createIndirectLight(Filament.engine, it)
//    }
//
//    // 2. Extract the actual IndirectLight from the bundle by accessing its 'indirectLight' property.
//    // This addresses the ClassCastException.
//    val indirectLight = iblBundle?.indirectLight
//
//    // 3. Get spherical harmonics separately.
//    // This addresses the previous "Unresolved reference: sphericalHarmonics" on the bundle.
//    val sphericalHarmonics = iblKtxBuffer?.let {
//        it.rewind() // Rewind again for this separate operation.
//        KTX1Loader.getSphericalHarmonics(it)
//    }
//
//    // 4. Create the skybox.
//    val skyboxBundle = skyboxKtxBuffer?.let {
//        it.rewind()
//        KTX1Loader.createSkybox(Filament.engine, it)
//    }
//    val skybox = skyboxBundle?.skybox
//
//    // 5. Return the new environment.
//    return KTXEnvironment(
//        indirectLight = indirectLight,
//        sphericalHarmonics = sphericalHarmonics,
//        skybox = skybox
//    )
//}

//Compatible for filament version <=1.45.0
@JvmOverloads
fun KTX1Loader.createEnvironment(
    iblKtxBuffer: Buffer?,
    skyboxKtxBuffer: Buffer? = null
) = KTXEnvironment(
    indirectLight = iblKtxBuffer?.let { createIndirectLight(Filament.engine, it).indirectLight },
    sphericalHarmonics = iblKtxBuffer?.rewind()?.let { getSphericalHarmonics(it) },
    skybox = skyboxKtxBuffer?.let { createSkybox(Filament.engine, it).skybox })

