package org.ZalJava.core;

import java.util.HashMap;

public class ResourceManager {

    public static final HashMap<String, Texture> textures = new HashMap<>();
    public static final HashMap<String, Shader> shaders = new HashMap<>();

    private ResourceManager(){}

    public static Texture getTexture(String name){
        if(!textures.containsKey(name)){
            throw new NullPointerException("Resource not found");
        }
        return textures.get(name);
    }
    public static void addTexture(String name, String textureName){
        Texture texture = new Texture(name, textureName);
        texture.compileTexture();
        textures.put(name, texture);
    }

    public static Shader getShader(String name){
        if(!shaders.containsKey(name)){
            throw new NullPointerException("Resource not found");
        }
        return shaders.get(name);
    }
    public static void addShader(String name, String vertexName, String fragmentName){
        Shader shader = new Shader(name, vertexName, fragmentName);
        shader.compileProgram();
        shaders.put(name, shader);
    }
}
