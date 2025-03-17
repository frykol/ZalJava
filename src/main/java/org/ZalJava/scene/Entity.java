package org.ZalJava.scene;

import org.ZalJava.core.Shader;
import org.ZalJava.core.Texture;
import org.ZalJava.core.VAO;
import org.ZalJava.core.Window;
import org.joml.Matrix4f;

import org.joml.Vector3f;


public abstract class Entity {
    private int id;
    protected String sceneHandle;
    private VAO vao = null;
    private Shader shader = null;
    private Matrix4f modelMatrix;
    private Texture texture;
    protected Vector3f position;
    private Vector3f color;
    private float scale = 1.0f;

    public Entity(String sceneHandle, Vector3f position){
        this.sceneHandle = sceneHandle;
        this.position = position;
        setupModelMatrix();
    }

    public Entity(String sceneHandle,VAO vao, Texture texture, Shader shader, Vector3f position, Vector3f color) {
        this.sceneHandle = sceneHandle;
        this.vao = vao;
        this.texture = texture;
        this.shader = shader;
        this.position = position;
        this.color = color;
        setupModelMatrix();
    }

    public Entity(String sceneHandle,Texture texture, Shader shader, Vector3f position,Vector3f color) {
        this.sceneHandle = sceneHandle;
        this.texture = texture;
        this.shader = shader;
        this.position = position;
        this.color = color;
        setupModelMatrix();
    }

    private void setupModelMatrix() {
        if(color == null){
            color = new Vector3f(1.0f, 1.0f, 1.0f);
        }
        modelMatrix = new Matrix4f().identity().translate(position);
    }

    public void rotate(Vector3f rotation) {
        float angleX = (float) Math.toRadians(rotation.x);
        float angleY = (float) Math.toRadians(rotation.y);
        float angleZ = (float) Math.toRadians(rotation.z);
        modelMatrix.rotateX(angleX);
        modelMatrix.rotateY(angleY);
        modelMatrix.rotateZ(angleZ);
    }
    public void scale(float scale){
        float fixedScale = (1.0f/this.scale) * scale;
        modelMatrix.scale(fixedScale);
        this.scale = scale;
    }

    public void setVAO(VAO vao) {
        this.vao = vao;
    }

    public VAO getVao() {
        return vao;
    }
    public Texture getTexture() {
        return texture;
    }
    public void setTexture(Texture texture) {
        this.texture = texture;
    }
    public Shader getShader() {
        return shader;
    }
    public void setShader(Shader shader) {
        this.shader = shader;
    }

    public Vector3f getPosition() {
        return position;
    }
    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public Matrix4f getModelMatrix(){
        return modelMatrix;
    }

    public Vector3f getColor() {
        return color;
    }
    public void setColor(Vector3f color) {
        this.color = color;
    }

    public float getScale() {
        return scale;
    }
    public int getId() {
        return id;
    }

    public void update(Window window) {}

    @Override
    public String toString(){
        String shaderString;
        String textureString;
        try{
            shaderString = shader.toString();
            textureString = texture.toString();
        }catch (Exception e){
            shaderString = "null";
            textureString = "null";
        }
        return this.getClass().getSimpleName() + " " + shaderString + " " + textureString + " " + position.x + " " + position.y + " " + position.z + " " + color.x + " " + color.y + " " + color.z + " " + scale;
    }

}
