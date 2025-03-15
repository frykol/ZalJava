package org.ZalJava;

import org.ZalJava.core.*;
import org.ZalJava.scene.*;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.*;
import org.lwjgl.glfw.*;


import java.util.ArrayList;


import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;



public class Main {


    private Window window;
    private Scene scene;
    private Shader shader;
    private Renderer renderer;

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

        ResourceManager.addTexture("brick", "brick.jpg");
        ResourceManager.addTexture("ball", "ball.jpg");

        shader = new Shader("basic.vertex", "basic.fragment");
        shader.compileProgram();

        float FOV = (float) Math.toRadians(60.0f);
        Matrix4f projMatrix = new Matrix4f();
        projMatrix.setPerspective(FOV, (float)1280/720, 0.01f, 1000.0f);
        shader.bind();
        shader.setUniformMatrix4f("u_Projection", projMatrix);


        renderer = new Renderer(shader);

        scene = new Scene();
        new Cube(scene, ResourceManager.getTexture("ball"),new Vector3f(0.0f,0.0f,0.0f), null);

        for(int i = 0; i<10; i++){
            new Cube(scene, ResourceManager.getTexture("brick"), new Vector3f((float)Math.random() * 10,(float)Math.random() * 10,(float)Math.random() * 10), null);
        }
    }

    private void loop() {
        float maxFPS = 1.0f/60.0f;
        float lastFrame = (float)glfwGetTime();
        float currentTime;
        float deltaTime = 0.0f;
        Player p = new Player(scene, new Vector3f(0.0f,0.0f,-10.0f));

        while ( !glfwWindowShouldClose(window.getWindow()) ) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            currentTime = (float)glfwGetTime();
            deltaTime += (currentTime - lastFrame)/maxFPS;
            lastFrame = currentTime;

            if(deltaTime >= 1.0f) {
                for(Entity entity : scene.getEntities()) {
                    //entity.scale((float) Math.sin(glfwGetTime()) * 0.5f + 1.0f);
                    entity.rotate(new Vector3f(1.0f, 0.5f, 0.0f));
                }

                //camera.update(window);
                p.update(window, scene);
                shader.bind();
                shader.setUniformMatrix4f("u_View", p.getCamera().getViewMatrix());
                deltaTime--;
            }
            for(Entity entity : scene.getEntities()) {
                renderer.renderEntity(entity);
            }


            glfwSwapBuffers(window.getWindow());
            glfwPollEvents();


        }
    }

    public static void main(String[] args) {
        new Main().run();
    }

}