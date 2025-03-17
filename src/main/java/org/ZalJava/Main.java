package org.ZalJava;

import org.ZalJava.core.*;
import org.ZalJava.scene.*;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.*;
import org.lwjgl.glfw.*;


import java.net.URL;
import java.util.ArrayList;


import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;



public class Main {


    private Window window;
    private SceneManager sceneManager;
    private Scene scene;
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

        ResourceManager.addShader("basic", "basic.vertex", "basic.fragment");
        ResourceManager.addShader("basicLight", "basic.vertex", "basicLight.fragment");

//        shader = new Shader("basic.vertex", "basic.fragment");
//        shader.compileProgram();

//        float FOV = (float) Math.toRadians(60.0f);
//        Matrix4f projMatrix = new Matrix4f();
//        projMatrix.setPerspective(FOV, (float)1280/720, 0.01f, 1000.0f);
//        shader.bind();
//        shader.setUniformMatrix4f("u_Projection", projMatrix);


        renderer = new Renderer(window.getProjectionMatrix());


        //scene = new Scene("test");
        //sceneManager.addScene("test", scene);
        //sceneManager.setCurrentScene("test");

        //new Cube(scene, ResourceManager.getTexture("ball"),ResourceManager.getShader("basic"), new Vector3f(0.0f,0.0f,-10.0f), new Vector3f(1.0f,1.0f,1.0f));

//        for(int i = 0; i<10; i++){
//            new Cube(scene, ResourceManager.getTexture("brick"),ResourceManager.getShader("basicLight"), new Vector3f((float)Math.random() * 10,(float)Math.random() * 10,(float)Math.random() * 10), null);
//        }
        SceneManager.init();
    }

    private void loop() {
        float maxFPS = 1.0f/60.0f;
        float lastFrame = (float)glfwGetTime();
        float currentTime;
        float deltaTime = 0.0f;
        //Player p = new Player(sceneManager.getCurrentScene(), new Vector3f(0.0f,0.0f,-10.0f));
        //sceneManager.saveCurrentScene();
        Scene currentScene = SceneManager.getCurrentScene();
        while ( !glfwWindowShouldClose(window.getWindow()) ) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            currentTime = (float)glfwGetTime();
            deltaTime += (currentTime - lastFrame)/maxFPS;
            lastFrame = currentTime;
            Player p = SceneManager.getCurrentScene().getPlayer();
            if(deltaTime >= 1.0f) {
//                for(Entity entity : currentScene.getEntities()) {
//                    //entity.scale((float) Math.sin(glfwGetTime()) * 0.5f + 1.0f);
//                    entity.rotate(new Vector3f(1.0f, 0.5f, 0.0f));
//                    entity.update(window);
//                }
                for(int i = 0; i<currentScene.getEntities().size(); i++) {
                    Entity e = currentScene.getEntities().get(i);
                    //e.scale((float) Math.sin(glfwGetTime()) * 0.5f + 1.0f);
                    e.rotate(new Vector3f(1.0f, 0.5f, 0.0f));
                    e.update(window);
                }

                //camera.update(window);
                //p.update(window, sceneManager.getCurrentScene());
                ResourceManager.getShader("basic").bind();
                ResourceManager.getShader("basic").setUniformMatrix4f("u_View", p.getCamera().getViewMatrix());
                ResourceManager.getShader("basicLight").bind();
                ResourceManager.getShader("basicLight").setUniformMatrix4f("u_View", p.getCamera().getViewMatrix());
                ResourceManager.getShader("basicLight").setUniform3f("u_Pos", 0.0f,0.0f,-10.0f);
                ResourceManager.getShader("basicLight").setUniform3f("u_PlayerPos", p.getPosition().x,p.getPosition().y,p.getPosition().z);
                deltaTime--;
            }
            for(Entity entity : SceneManager.getCurrentScene().getEntities()) {
                renderer.renderEntity(entity);
            }

            if(window.isKeyPressedOnce(GLFW_KEY_G)) {
                SceneManager.saveCurrentScene();
            }

            glfwSwapBuffers(window.getWindow());
            glfwPollEvents();


        }
    }

    public static void main(String[] args) {
        new Main().run();
    }

}