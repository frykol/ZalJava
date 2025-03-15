package org.ZalJava.core;

import org.ZalJava.Main;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryStack;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.Objects;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.stb.STBImage.stbi_image_free;
import static org.lwjgl.stb.STBImage.stbi_load;

public class Texture {
    private int ID;
    private int width;
    private int height;
    private int numberOfChannels;
    String textureName;

    private int status = 0;

    public Texture(String textureName){
        this.textureName = textureName;
    }

    public void compileTexture(){
        if(status != 0){
            return;
        }

        ID = GL30.glGenTextures();
        GL30.glBindTexture(GL_TEXTURE_2D, ID);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL30.GL_CLAMP);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL30.GL_CLAMP);

        IntBuffer w;
        IntBuffer h;
        IntBuffer channels;

        try (MemoryStack stack = MemoryStack.stackPush()){
            w = stack.mallocInt(1);
            h = stack.mallocInt(1);
            channels = stack.mallocInt(1);
        }catch (Exception e){
            status = -1;
            return;
        }

        String s;
        try {
            s = Objects.requireNonNull(Main.class.getClassLoader().getResource(textureName)).getPath().substring(1);
        }catch (NullPointerException e){
            System.out.println("Texture '" + textureName + "' not found");
            status = -1;
            return;
        }
        System.out.println(s);

        ByteBuffer buf = stbi_load(s, w, h, channels, 4);
        if(buf == null) {
            System.out.println("Failed to load texture " + textureName);
            status = -1;
            return;
        }

        width = w.get();
        height = h.get();
        numberOfChannels = channels.get();

        GL30.glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, buf);
        GL30.glGenerateMipmap(GL_TEXTURE_2D);
        glBindTexture(GL_TEXTURE_2D, 0);
        stbi_image_free(buf);

        status = 1;
    }
    public void bind(){
        if(status != 1){
            System.out.println("Could not bind Texture " + textureName);
            return;
        }
        GL30.glBindTexture(GL_TEXTURE_2D, ID);
    }

    public void unBind(){
        GL30.glBindTexture(GL_TEXTURE_2D, 0);
    }

}
