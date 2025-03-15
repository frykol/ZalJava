package org.ZalJava.scene;

import org.ZalJava.core.Window;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import static org.lwjgl.glfw.GLFW.*;

public class Camera {
    private Matrix4f viewMatrix;
    private Vector3f position;
    private Vector3f cameraFront;
    private float cameraSpeed;
    private boolean hasMoved = true;
    private float lastX;
    private float lastY;
    private float yaw = 90.0f;
    private float pitch = 0.0f;

    public Camera(float cameraSpeed) {
        this.cameraSpeed = cameraSpeed;
        initCamera();
    }

    private void initCamera(){
        viewMatrix = new Matrix4f().identity().translate(0.0f,0.0f,-10.0f);
        position = new Vector3f();
        cameraFront = new Vector3f(0.0f,0.0f,1.0f);
    }

    public Matrix4f getViewMatrix() {
        return viewMatrix;
    }
    public void update(Window window) {
        if(window.isKeyPressedOnce(GLFW_KEY_ENTER)){
            glfwSetInputMode(window.getWindow(), GLFW_CURSOR, GLFW_CURSOR_DISABLED);
        }
        if(window.isKeyPressedOnce(GLFW_KEY_ESCAPE)){
            glfwSetInputMode(window.getWindow(), GLFW_CURSOR, GLFW_CURSOR_NORMAL);
        }

//        if (window.isKeyPressed(GLFW_KEY_W)) {
//            position.add(cameraFront);
//        }
//        if (window.isKeyPressed(GLFW_KEY_S)) {
//            position.sub(cameraFront);
//        }
//        if (window.isKeyPressed(GLFW_KEY_A)) {
//            position.sub(cameraFront.cross(new Vector3f(0.0f, 1.0f, 0.0f), new Vector3f()).normalize());
//            //position.sub(new Vector3f().normalize(cameraFront.cross(new Vector3f(0.0f, 1.0f, 0.0f), new Vector3f())));
//        }
//        if (window.isKeyPressed(GLFW_KEY_D)) {
//            position.add(cameraFront.cross(new Vector3f(0.0f, 1.0f, 0.0f), new Vector3f()).normalize());
//        }

        float mouseX = (float)window.getMouseX();
        float mouseY = (float)window.getMouseY();
        if(hasMoved){
            lastX = mouseX;
            lastY = mouseY;
            hasMoved = false;
        }
        float xoffset = mouseX - lastX;
        float yoffset = lastY - mouseY; // reversed since y-coordinates go from bottom to top
        lastX = mouseX;
        lastY = mouseY;

        float sensitivity = 0.1f; // change this value to your liking
        xoffset *= sensitivity;
        yoffset *= sensitivity;

        yaw += xoffset;
        pitch += yoffset;

        if (pitch > 89.0f)
            pitch = 89.0f;
        if (pitch < -89.0f)
            pitch = -89.0f;

        Vector3f front = new Vector3f();
        front.x = (float)Math.cos(Math.toRadians(yaw)) * (float)Math.cos(Math.toRadians(pitch));
        front.y = (float)Math.sin(Math.toRadians(pitch));
        front.z = (float)Math.sin(Math.toRadians(yaw)) * (float)Math.cos(Math.toRadians(pitch));
        cameraFront = front.normalize();

        viewMatrix = new Matrix4f().lookAt(position, position.add(cameraFront, new Vector3f()), new Vector3f(0.0f,1.0f,0.0f));
    }
    public Vector3f getCameraFront(){
        return cameraFront;
    }
    public void setPosition(Vector3f position){
        this.position = position;
    }
}
