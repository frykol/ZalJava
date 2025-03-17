package org.ZalJava.scene;

import org.ZalJava.core.ResourceManager;
import org.ZalJava.core.Texture;
import org.ZalJava.core.Window;
import org.joml.Vector3f;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;

public class Player extends Entity{
    private Camera camera;

    public Player(String sceneHandle, Vector3f position) {
        super(sceneHandle, position);
        initPlayer();
    }
    private void initPlayer(){
        camera = new Camera(1);
    }
    @Override
    public void update(Window window){

        if (window.isKeyPressed(GLFW_KEY_W)) {
            position.add(camera.getCameraFront());
        }
        if (window.isKeyPressed(GLFW_KEY_S)) {
            position.sub(camera.getCameraFront());
        }
        if (window.isKeyPressed(GLFW_KEY_A)) {
            position.sub(camera.getCameraFront().cross(new Vector3f(0.0f, 1.0f, 0.0f), new Vector3f()).normalize());
            //position.sub(new Vector3f().normalize(cameraFront.cross(new Vector3f(0.0f, 1.0f, 0.0f), new Vector3f())));
        }
        if (window.isKeyPressed(GLFW_KEY_D)) {
            position.add(camera.getCameraFront().cross(new Vector3f(0.0f, 1.0f, 0.0f), new Vector3f()).normalize());
        }
        if (window.isKeyPressed(GLFW_KEY_SPACE)) {
            position.add(new Vector3f(0.0f, 1.0f, 0.0f));
        }
        if (window.isKeyPressed(GLFW_KEY_LEFT_SHIFT)) {
            position.sub(new Vector3f(0.0f, 1.0f, 0.0f));
        }

        if(window.isKeyPressedOnce(GLFW_KEY_F)){
            Vector3f pos = new Vector3f(position.x, position.y, position.z);
            Cube c = new Cube(sceneHandle, ResourceManager.getTexture("brick"),ResourceManager.getShader("basicLight"), pos, new Vector3f(1.0f, 0.2f, 0.0f));
            //SceneManager.getCurrentScene().addEntity(c);
            SceneManager.getScene(sceneHandle).addEntity(c);
        }
        camera.setPosition(position);
        camera.update(window);
    }

    public Camera getCamera(){
        return camera;
    }
}
