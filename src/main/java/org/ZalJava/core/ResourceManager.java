package org.ZalJava.core;

import java.util.HashMap;

public class ResourceManager {

    public static final HashMap<String, Texture> textures = new HashMap<>();

    private ResourceManager(){}

    public static Texture getTexture(String name){
        if(!textures.containsKey(name)){
            throw new NullPointerException("Resource not found");
        }
        return textures.get(name);
    }
    public static void addTexture(String name, String textureName){
        Texture texture = new Texture(textureName);
        texture.compileTexture();
        textures.put(name, texture);
    }
}
