package org.ZalJava;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;


import java.util.ArrayList;


import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;



public class Main {


    private Window window;

    private final ArrayList<VAO> vaos = new ArrayList<>();
    private Shader shader;
    private Texture texture;


    public void run() {
        System.out.println("Hello LWJGL " + Version.getVersion() + "!");

        init();
        loop();

        window.destroyWindow();

        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    private void init() {
        GLFWErrorCallback.createPrint(System.err).set();

        window = new Window(1280, 720);
        window.init();


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
        try {
            VAO vao = new VAO(vbo);
            System.out.println(vao.getVertesies());
            vaos.add(vao);
        }
        catch (RuntimeException e){
            System.err.println("Error creating VAO");
        }

        shader = new Shader("basic.vertex", "basic.fragment");
        shader.compileProgram();

        float FOV = (float) Math.toRadians(60.0f);
        Matrix4f projMatrix = new Matrix4f();
        projMatrix.setPerspective(FOV, (float)1280/720, 0.01f, 1000.0f);
        shader.bind();
        shader.setUniformMatrix4f("u_Projection", projMatrix);




        Matrix4f viewMatrix = new Matrix4f();
        viewMatrix.identity();
        viewMatrix.translate(new Vector3f(0.0f,0.0f,-10.0f));
        shader.setUniformMatrix4f("u_View", viewMatrix);

        texture = new Texture("brick.jpg");
        texture.compileTexture();
    }

    private void loop() {
        Matrix4f modelMatrix = new Matrix4f();
        modelMatrix.identity();


        modelMatrix.translate(new Vector3f(-6.0f, 2.0f, 0.0f));


        while ( !glfwWindowShouldClose(window.getWindow()) ) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            texture.bind();
            modelMatrix.rotate(0.05f * (float) Math.toRadians(10.0f), new Vector3f(1.0f,0.5f,0.0f));
            shader.setUniform3f("u_Color", 1.0f, 0.3f, 0.3f);
            shader.setUniformMatrix4f("u_Model", modelMatrix);
            for (VAO vao : vaos) {
                vao.bind();
                GL20.glDrawArrays(GL11.GL_TRIANGLES, 0, vao.getVertesies());
            }


            glfwSwapBuffers(window.getWindow());
            glfwPollEvents();
        }
    }

    public static void main(String[] args) {
        new Main().run();
    }

}