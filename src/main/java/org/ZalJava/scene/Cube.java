package org.ZalJava.scene;

import org.ZalJava.core.Texture;
import org.ZalJava.core.VAO;
import org.ZalJava.core.VBO;
import org.joml.Vector3f;

public class Cube extends Entity {

    public Cube(Scene scene, Texture texture, Vector3f position, Vector3f color) {
        super(scene, texture, position, color);
        setupVAO();
    }

    private void setupVAO(){
        float[] data = {
                -1.0f, -1.0f,  1.0f,  0.0f, 0.0f,  // Wierzchołek 1 (x, y, z, u, v)
                1.0f, -1.0f,  1.0f,  1.0f, 0.0f,  // Wierzchołek 2 (x, y, z, u, v)
                1.0f,  1.0f,  1.0f,  1.0f, 1.0f,  // Wierzchołek 3 (x, y, z, u, v)
                -1.0f, -1.0f,  1.0f,  0.0f, 0.0f,  // Wierzchołek 4 (x, y, z, u, v)
                1.0f,  1.0f,  1.0f,  1.0f, 1.0f,  // Wierzchołek 5 (x, y, z, u, v)
                -1.0f,  1.0f,  1.0f,  0.0f, 1.0f,  // Wierzchołek 6 (x, y, z, u, v)

                // Tylna ściana (-z)
                -1.0f, -1.0f, -1.0f,  0.0f, 0.0f,  // Wierzchołek 1 (x, y, z, u, v)
                -1.0f,  1.0f, -1.0f,  1.0f, 0.0f,  // Wierzchołek 2 (x, y, z, u, v)
                1.0f,  1.0f, -1.0f,  1.0f, 1.0f,  // Wierzchołek 3 (x, y, z, u, v)
                -1.0f, -1.0f, -1.0f,  0.0f, 0.0f,  // Wierzchołek 4 (x, y, z, u, v)
                1.0f,  1.0f, -1.0f,  1.0f, 1.0f,  // Wierzchołek 5 (x, y, z, u, v)
                1.0f, -1.0f, -1.0f,  0.0f, 1.0f,  // Wierzchołek 6 (x, y, z, u, v)

                // Lewa ściana (-x)
                -1.0f, -1.0f, -1.0f,  0.0f, 0.0f,  // Wierzchołek 1 (x, y, z, u, v)
                -1.0f, -1.0f,  1.0f,  1.0f, 0.0f,  // Wierzchołek 2 (x, y, z, u, v)
                -1.0f,  1.0f,  1.0f,  1.0f, 1.0f,  // Wierzchołek 3 (x, y, z, u, v)
                -1.0f, -1.0f, -1.0f,  0.0f, 0.0f,  // Wierzchołek 4 (x, y, z, u, v)
                -1.0f,  1.0f,  1.0f,  1.0f, 1.0f,  // Wierzchołek 5 (x, y, z, u, v)
                -1.0f,  1.0f, -1.0f,  0.0f, 1.0f,  // Wierzchołek 6 (x, y, z, u, v)

                // Prawa ściana (x)
                1.0f, -1.0f, -1.0f,  0.0f, 0.0f,  // Wierzchołek 1 (x, y, z, u, v)
                1.0f,  1.0f, -1.0f,  1.0f, 0.0f,  // Wierzchołek 2 (x, y, z, u, v)
                1.0f,  1.0f,  1.0f,  1.0f, 1.0f,  // Wierzchołek 3 (x, y, z, u, v)
                1.0f, -1.0f, -1.0f,  0.0f, 0.0f,  // Wierzchołek 4 (x, y, z, u, v)
                1.0f,  1.0f,  1.0f,  1.0f, 1.0f,  // Wierzchołek 5 (x, y, z, u, v)
                1.0f, -1.0f,  1.0f,  0.0f, 1.0f,  // Wierzchołek 6 (x, y, z, u, v)

                // Górna ściana (y)
                -1.0f,  1.0f, -1.0f,  0.0f, 0.0f,  // Wierzchołek 1 (x, y, z, u, v)
                -1.0f,  1.0f,  1.0f,  1.0f, 0.0f,  // Wierzchołek 2 (x, y, z, u, v)
                1.0f,  1.0f,  1.0f,  1.0f, 1.0f,  // Wierzchołek 3 (x, y, z, u, v)
                -1.0f,  1.0f, -1.0f,  0.0f, 0.0f,  // Wierzchołek 4 (x, y, z, u, v)
                1.0f,  1.0f,  1.0f,  1.0f, 1.0f,  // Wierzchołek 5 (x, y, z, u, v)
                1.0f,  1.0f, -1.0f,  0.0f, 1.0f,  // Wierzchołek 6 (x, y, z, u, v)

                // Dolna ściana (-y)
                -1.0f, -1.0f, -1.0f,  0.0f, 0.0f,  // Wierzchołek 1 (x, y, z, u, v)
                1.0f, -1.0f, -1.0f,  1.0f, 0.0f,  // Wierzchołek 2 (x, y, z, u, v)
                1.0f, -1.0f,  1.0f,  1.0f, 1.0f,  // Wierzchołek 3 (x, y, z, u, v)
                -1.0f, -1.0f, -1.0f,  0.0f, 0.0f,  // Wierzchołek 4 (x, y, z, u, v)
                1.0f, -1.0f,  1.0f,  1.0f, 1.0f,  // Wierzchołek 5 (x, y, z, u, v)
                -1.0f, -1.0f,  1.0f,  0.0f, 1.0f
        };
        VBO vbo = new VBO(data);
        vbo.addAttribute(3);
        vbo.addAttribute(2);
        VAO vao = new VAO(vbo);
        setVAO(vao);

    }

}
