package org.ZalJava.core;

import org.ZalJava.scene.Entity;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

public class Renderer {
    private final Matrix4f projectionMatrix;

    public Renderer(Matrix4f projectionMatrix) {

        this.projectionMatrix = projectionMatrix;
    }


    public void render(Shader shader, VAO vao, Texture texture, Vector3f position, float rotation, Vector3f rotationAxis) {
        shader.bind();
        texture.bind();

        Matrix4f modelMatrix = new Matrix4f();
        modelMatrix.identity();
        modelMatrix.translate(position);
        modelMatrix.rotate(rotation, rotationAxis);
        shader.setUniform3f("u_Color", 1.0f, 0.3f, 0.3f);
        shader.setUniformMatrix4f("u_Model", modelMatrix);

        vao.bind();

        GL20.glDrawArrays(GL11.GL_TRIANGLES, 0, vao.getVertesies());

        vao.unbind();

        texture.unBind();
        shader.unBind();
    }

    public void renderEntity(Entity entity){
        if(entity.getVao() == null)
            return;

        Shader shader = entity.getShader();
        shader.bind();
        Vector3f color = entity.getColor();
        shader.setUniformMatrix4f("u_Projection", projectionMatrix);
        shader.setUniform3f("u_Color", color.x, color.y, color.z);
        Matrix4f modelMatrix = entity.getModelMatrix();
        shader.setUniformMatrix4f("u_Model", modelMatrix);
        entity.getTexture().bind();
        entity.getVao().bind();
        GL20.glDrawArrays(GL11.GL_TRIANGLES, 0, entity.getVao().getVertesies());
        entity.getVao().unbind();
        entity.getTexture().unBind();
        shader.unBind();
    }
}
