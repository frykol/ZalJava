package org.ZalJava.scene;

import org.ZalJava.core.Texture;
import org.ZalJava.core.VAO;
import org.joml.Matrix4f;

import org.joml.Vector3f;


public abstract class Entity {
    private int id;
    private VAO vao = null;
    private Matrix4f modelMatrix;
    private Texture texture;
    protected Vector3f position;
    private Vector3f color;
    private float scale = 1.0f;

    public Entity(Scene scene, Vector3f position){
        this.position = position;
        setupModelMatrix();
        scene.addEntity(this);
    }

    public Entity(Scene scene,VAO vao, Texture texture, Vector3f position, Vector3f color) {
        this.vao = vao;
        this.texture = texture;
        this.position = position;
        this.color = color;
        setupModelMatrix();
        scene.addEntity(this);
    }

    public Entity(Scene scene,Texture texture, Vector3f position,Vector3f color) {
        this.texture = texture;
        this.position = position;
        this.color = color;
        setupModelMatrix();
        scene.addEntity(this);
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

}
