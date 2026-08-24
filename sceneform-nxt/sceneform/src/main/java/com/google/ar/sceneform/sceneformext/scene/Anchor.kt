package com.google.ar.sceneform.sceneformext.scene

import com.google.ar.core.Anchor

fun Anchor.destroy() {
    detach()
}