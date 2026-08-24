package com.google.ar.sceneform.rendering;

import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.utilities.AndroidPreconditions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import androidx.annotation.Nullable;

public class PlaneFactory {
    private static final int COORDS_PER_TRIANGLE = 3;

    /**
     * Creates a {@link ModelRenderable} in the shape of a plane with the given specifications.
     *
     * @param size     the size of the constructed plane
     * @param center   the center of the constructed plane
     * @param material the material to use for rendering the plane
     * @return renderable representing a plane with the given parameters
     */
    @SuppressWarnings("AndroidApiChecker")
    // CompletableFuture requires api level 24
    public static ModelRenderable makePlane(Vector3 size, Vector3 center, Material material) {
        AndroidPreconditions.checkMinAndroidApiLevel();

        Vector3 extents = size.scaled(0.5f);

        Vector3 p0 = Vector3.add(center, new Vector3(-extents.x, -extents.y, extents.z));
        Vector3 p1 = Vector3.add(center, new Vector3(-extents.x, extents.y, -extents.z));
        Vector3 p2 = Vector3.add(center, new Vector3(extents.x, extents.y, -extents.z));
        Vector3 p3 = Vector3.add(center, new Vector3(extents.x, -extents.y, extents.z));

        Vector3 front = new Vector3();

        Vertex.UvCoordinate uv00 = new Vertex.UvCoordinate(0.0f, 0.0f);
        Vertex.UvCoordinate uv10 = new Vertex.UvCoordinate(1.0f, 0.0f);
        Vertex.UvCoordinate uv01 = new Vertex.UvCoordinate(0.0f, 1.0f);
        Vertex.UvCoordinate uv11 = new Vertex.UvCoordinate(1.0f, 1.0f);

        ArrayList<Vertex> vertices = new ArrayList<>(
                Arrays.asList(
                        Vertex.builder().setPosition(p0).setNormal(front).setUvCoordinate(uv00).build(),
                        Vertex.builder().setPosition(p1).setNormal(front).setUvCoordinate(uv01).build(),
                        Vertex.builder().setPosition(p2).setNormal(front).setUvCoordinate(uv11).build(),
                        Vertex.builder().setPosition(p3).setNormal(front).setUvCoordinate(uv10).build()
                )
        );

        final int trianglesPerSide = 2;

        ArrayList<Integer> triangleIndices = new ArrayList<>(trianglesPerSide * COORDS_PER_TRIANGLE);
        // First triangle.
        triangleIndices.add(3);
        triangleIndices.add(1);
        triangleIndices.add(0);

        // Second triangle.
        triangleIndices.add(3);
        triangleIndices.add(2);
        triangleIndices.add(1);

        RenderableDefinition.Submesh submesh = RenderableDefinition.Submesh.builder()
                .setTriangleIndices(triangleIndices)
                .setMaterial(material)
                .build();

        RenderableDefinition renderableDefinition = RenderableDefinition.builder()
                .setVertices(vertices)
                .setSubmeshes(Arrays.asList(submesh))
                .build();

        CompletableFuture<ModelRenderable> future = ModelRenderable.builder()
                .setSource(renderableDefinition)
                .build();

        @Nullable ModelRenderable result;
        try {
            result = future.get();
        } catch (ExecutionException | InterruptedException ex) {
            throw new AssertionError("Error creating renderable.", ex);
        }

        if (result == null) {
            throw new AssertionError("Error creating renderable.");
        }

        return result;
    }

    public static ModelRenderable makeRoundedPlane(
            Vector3 size, Vector3 center, float radius, Material material) {

        float width = size.x;
        float height = size.y;

        int cornerSegments = 24;//12 for less smooth

        List<Vertex> vertices = new ArrayList<>();
        List<Integer> indices = new ArrayList<>();

        float hw = width / 2f;
        float hh = height / 2f;

        // Center vertex
        vertices.add(Vertex.builder()
                .setPosition(center)
                .setNormal(new Vector3(0, 0, 1))
                .setUvCoordinate(new Vertex.UvCoordinate(0.5f, 0.5f))
                .build());

        int centerIndex = 0;
        int startIndex = 1;

        float[][] corners = {
                { hw - radius,  hh - radius}, // top right
                {-hw + radius,  hh - radius}, // top left
                {-hw + radius, -hh + radius}, // bottom left
                { hw - radius, -hh + radius}  // bottom right
        };

        float[] startAngles = {0, 90, 180, 270};

        for (int c = 0; c < 4; c++) {

            float cx = corners[c][0];
            float cy = corners[c][1];

            for (int i = 0; i <= cornerSegments; i++) {

                float angle = (float) Math.toRadians(
                        startAngles[c] + (90f * i / cornerSegments)
                );

                float x = cx + radius * (float) Math.cos(angle);
                float y = cy + radius * (float) Math.sin(angle);

                float u = (x + hw) / width;
                float v = (y + hh) / height;

                vertices.add(Vertex.builder()
                        .setPosition(new Vector3(x + center.x, y + center.y, center.z))
                        .setNormal(new Vector3(0, 0, 1))
                        .setUvCoordinate(new Vertex.UvCoordinate(u, v))
                        .build());
            }
        }

        int vertexCount = vertices.size();

        for (int i = startIndex; i < vertexCount; i++) {

            int next = i + 1;
            if (next >= vertexCount)
                next = startIndex;

            indices.add(centerIndex);
            indices.add(i);
            indices.add(next);
        }

        RenderableDefinition.Submesh submesh =
                RenderableDefinition.Submesh.builder()
                        .setTriangleIndices(indices)
                        .setMaterial(material)
                        .build();

        RenderableDefinition definition =
                RenderableDefinition.builder()
                        .setVertices(vertices)
                        .setSubmeshes(Collections.singletonList(submesh))
                        .build();

        try {
            return ModelRenderable.builder()
                    .setSource(definition)
                    .build()
                    .get(); // synchronous return
        } catch (Exception e) {
            throw new RuntimeException("Failed to create rounded plane", e);
        }
    }

}


