package org.ZalJava;

import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import java.io.File;
import java.io.FileNotFoundException;

import java.net.URL;
import java.nio.FloatBuffer;
import java.util.Scanner;

public class Shader {
    private int shaderProgram;
    private int statusCode = 0;

    private final String vertexShader;
    private final String fragmentShader;

    Shader(String vertexFile, String fragmentFile) {
        this.vertexShader = vertexFile;
        this.fragmentShader = fragmentFile;
    }


    public void compileProgram(){
        if(statusCode != 0){
            return;
        }

        StringBuilder vertexSource;
        StringBuilder fragmentSource;

        try {
            vertexSource = readShader(vertexShader);
            fragmentSource =  readShader(fragmentShader);
        } catch (FileNotFoundException | NullPointerException e){
            statusCode = -1;
            return;
        }

        int vertexShader;
        int fragmentShader;

        try{
            vertexShader = compileShader(GL30.GL_VERTEX_SHADER, vertexSource);
            fragmentShader = compileShader(GL30.GL_FRAGMENT_SHADER, fragmentSource);
        }catch(Exception e){
            statusCode = -1;
            return;
        }

        shaderProgram = GL30.glCreateProgram();
        GL30.glAttachShader(shaderProgram, vertexShader);
        GL30.glAttachShader(shaderProgram, fragmentShader);
        GL30.glLinkProgram(shaderProgram);

        GL30.glDeleteShader(vertexShader);
        GL30.glDeleteShader(fragmentShader);

        statusCode = 1;
    }


    private int compileShader(int shaderType, StringBuilder shaderSource) throws Exception {
        int shader = GL30.glCreateShader(shaderType);
        GL30.glShaderSource(shader, shaderSource);
        GL30.glCompileShader(shader);
        if(GL20.glGetShaderi(shader, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE){
            throw new Exception("Error compiling shader: " + GL20.glGetShaderInfoLog(shader));
        }
        return shader;
    }

    public void setUniform3f(String name, float x, float y, float z){
        GL30.glUniform3f(GL30.glGetUniformLocation(shaderProgram, name), x, y, z);
    }

    public void setUniformMatrix4f(String name, Matrix4f matrix){
        FloatBuffer f = BufferUtils.createFloatBuffer(16);
        matrix.get(f);
        GL30.glUniformMatrix4fv(GL30.glGetUniformLocation(shaderProgram, name), false, f);
    }

    void bind(){
        if(statusCode != 1){
            System.err.println("Cannot bind program");
            return;
        }
        GL30.glUseProgram(shaderProgram);
    }

    void unBind(){
        GL30.glUseProgram(0);
    }

    private StringBuilder readShader(String shaderPath) throws FileNotFoundException {
        URL path;
        try {
            path = Main.class.getClassLoader().getResource(shaderPath);
        }
        catch (NullPointerException e){
            System.err.println("Null shaderPath");
            throw new NullPointerException("Null shaderPath");
        }

        if(path == null){
            System.err.println("Shader not found");
            throw new FileNotFoundException("Shader not found");
        }

        File file = new File(path.getFile());
        Scanner scanner = new Scanner(file);
        StringBuilder shader = new StringBuilder();
        while (scanner.hasNextLine()){
            shader.append(scanner.nextLine()).append("\n");
        }
        return shader;
    }
}
