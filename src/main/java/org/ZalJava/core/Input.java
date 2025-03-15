package org.ZalJava.core;

import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.glfw.GLFW.*;

public class Input {
    private final Map<Integer, Boolean> keyStates = new HashMap<>();
    private final Map<Integer, Boolean> keyStatesInThisFrame = new HashMap<>();

    public Input(){

    }

    public void setKey(int key, int action){
        if(action == GLFW_PRESS){
            keyStates.put(key, true);
            keyStatesInThisFrame.put(key, true);
        }
        else if(action == GLFW_RELEASE){
            keyStates.put(key, false);
            keyStatesInThisFrame.put(key, false);
        }
    }

    public boolean isKeyPressed(int key){
        return keyStates.containsKey(key) && keyStates.get(key);
    }
    public boolean isKeyPressedOnce(int key){
        if(keyStatesInThisFrame.containsKey(key) && keyStatesInThisFrame.get(key)){
            keyStatesInThisFrame.put(key, false);
            return true;
        }
        return false;
    }
}
