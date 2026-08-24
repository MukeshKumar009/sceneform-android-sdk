package com.google.ar.sceneform.ux;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.ar.core.CameraConfig;
import com.google.ar.core.CameraConfigFilter;
import com.google.ar.core.Config;
import com.google.ar.core.Session;
import com.google.ar.sceneform.sceneformext.ArSceneViewKt;
import com.google.ar.sceneform.sceneformext.light.LightEstimationConfig;

/**
 * Implements ArFragment and configures the session for using the augmented faces feature.
 */
public class ArFrontFacingFragment extends ArFragment {

    @Override
    protected Config onCreateSessionConfig(Session session) {
        CameraConfigFilter filter = new CameraConfigFilter(session);
        filter.setFacingDirection(CameraConfig.FacingDirection.FRONT);

        session.setCameraConfig(session.getSupportedCameraConfigs(filter).get(0));

        Config config = super.onCreateSessionConfig(session);
        config.setPlaneFindingMode(Config.PlaneFindingMode.DISABLED);
        config.setAugmentedFaceMode(Config.AugmentedFaceMode.MESH3D);
        config.setLightEstimationMode(Config.LightEstimationMode.DISABLED);

        return config;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getInstructionsController().setEnabled(false);

        // Disable the light estimation mode because it's not compatible with the front face camera
        ArSceneViewKt.setLightEstimationConfig(getArSceneView(), LightEstimationConfig.DISABLED);

        // Hide plane indicating dots
        getArSceneView().getPlaneRenderer().setVisible(false);
        // Disable the rendering of detected planes.
        getArSceneView().getPlaneRenderer().setEnabled(false);
    }
}
