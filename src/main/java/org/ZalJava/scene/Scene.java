package org.ZalJava.scene;

import java.util.ArrayList;

public class Scene {
    private final ArrayList<Entity> entities = new ArrayList<>();

    public Scene() {

    }

    public void addEntity(Entity entity) {
        entities.add(entity);
    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }

}
