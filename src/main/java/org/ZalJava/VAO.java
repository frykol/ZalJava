package org.ZalJava;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import java.util.ArrayList;

public class VAO {
    private int ID;
    private int vertesies;

    VAO(VBO vbo){
        vbo.check();
        createVAO(vbo);
    }

    private void createVAO(VBO vbo){
        vertesies = vbo.getNumberOfVertesies();
        ID = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(ID);
        vbo.bind();
        ArrayList<Integer> numberOfAttributes = vbo.getNumberOfAttributes();
        int lastAttribute = 0;
        for(int i = 0; i < numberOfAttributes.size(); i++){
            int attribute = numberOfAttributes.get(i);
            GL20.glEnableVertexAttribArray(i);
            GL20.glVertexAttribPointer(i, attribute, GL11.GL_FLOAT, false, vbo.getStride()*VBO.SIZE_OF_FLOAT, (long) lastAttribute * VBO.SIZE_OF_FLOAT);
            lastAttribute += attribute;
        }
        vbo.unbind();
        GL30.glBindVertexArray(0);
    }

    public int getVertesies(){
        return vertesies;
    }

    public void bind(){
        GL30.glBindVertexArray(ID);
    }
    public void unbind(){
        GL30.glBindVertexArray(0);
    }
}
