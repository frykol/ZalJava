package org.ZalJava.core;

import org.joml.Matrix4f;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {
    private long window;
    private int width, height;

    private Input input;
    private Matrix4f projectionMatrix;

    private double mouseX, mouseY;

    public Window(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void init(){
        if ( !glfwInit() )
            throw new IllegalStateException("Unable to initialize GLFW");

        // Configure GLFW
        //glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);
        // Create the window
        window = glfwCreateWindow(1280, 720, "Hello World!", NULL, NULL);
        if ( window == NULL )
            throw new RuntimeException("Failed to create the GLFW window");

        input = new Input();


        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            input.setKey(key, action);
        });

        glfwSetCursorPosCallback(window, (window, xpos, ypos) -> {
                this.mouseX = xpos;
                this.mouseY = ypos;
        });

        try ( MemoryStack stack = stackPush() ) {
            IntBuffer pWidth = stack.mallocInt(1); // int*
            IntBuffer pHeight = stack.mallocInt(1); // int*

            // Get the window size passed to glfwCreateWindow
            glfwGetWindowSize(window, pWidth, pHeight);

            // Get the resolution of the primary monitor
            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            if(vidmode == null){
                throw new RuntimeException("Failed to get primary video mode");
            }

            // Center the window
            glfwSetWindowPos(
                    window,
                    (vidmode.width() - pWidth.get(0)) / 2,
                    (vidmode.height() - pHeight.get(0)) / 2
            );
        }

        glfwMakeContextCurrent(window);
        // Enable v-sync
        glfwSwapInterval(1);

        // Make the window visible
        glfwShowWindow(window);
        GL.createCapabilities();
        glEnable(GL_DEPTH_TEST);
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        float FOV = (float) Math.toRadians(60.0f);
        projectionMatrix = new Matrix4f();
        projectionMatrix.setPerspective(FOV, (float)1280/720, 0.01f, 1000.0f);
    }

    public void destroyWindow(){
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);
    }

    public long getWindow(){
        return window;
    }

    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
    public double getMouseX(){
        return mouseX;
    }
    public double getMouseY(){
        return mouseY;
    }

    public Matrix4f getProjectionMatrix(){
        return projectionMatrix;
    }

    public void setWindow(int width, int height) {
        this.width = width;
        this.height = height;
    }
    public boolean isKeyPressed(int keyCode){
        return input.isKeyPressed(keyCode);
    }
    public boolean isKeyPressedOnce(int keyCode){
        return input.isKeyPressedOnce(keyCode);
    }
}
