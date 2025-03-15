package org.ZalJava.core;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL15;

import java.nio.FloatBuffer;
import java.util.ArrayList;

public class VBO {
    private int ID;

    private final ArrayList<Integer> numberOfAttributes = new ArrayList<>();
    private int dataSize;

    public static final int SIZE_OF_FLOAT = 4;


    public VBO(float[] data){
        createBuffer(data);
    }
    private void createBuffer(float[] data){
        dataSize = data.length;
        ID = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, ID);
        FloatBuffer buf = BufferUtils.createFloatBuffer(data.length);
        buf.put(data);
        buf.flip();
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buf, GL15.GL_STATIC_DRAW);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
    }

    public void addAttribute(int attribute){
        numberOfAttributes.add(attribute);
    }

    public void check(){
        int sum = numberOfAttributes.stream().mapToInt(i -> i).sum();
        if(dataSize % sum != 0){
            throw new RuntimeException("Data size is not a multiple of " + dataSize);
        }
    }

    public int getNumberOfVertesies(){
        int sum = numberOfAttributes.stream().mapToInt(i -> i).sum();
        return dataSize / sum;
    }

    public ArrayList<Integer> getNumberOfAttributes() {
        return numberOfAttributes;
    }

    public int getStride(){
        return numberOfAttributes.stream().mapToInt(i -> i).sum();
    }

    public void bind(){
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, ID);
    }

    public void unbind(){
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
    }

}
