package org.ZalJava.scene;

import java.util.ArrayList;

public class Scene {
    private final ArrayList<Entity> entities = new ArrayList<>();
    private final String path;

    public Scene(String path) {
        this.path = path;
    }

    public void addEntity(Entity entity) {
            entities.add(entity);
    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }

    public Player getPlayer() {
        if (entities.isEmpty()) {
            return null;
        }
        for (Entity e : entities) {
            if (e instanceof Player) {
                return (Player) e;
            }
        }
        return null;
    }

    public String getPath() {
        return path;
    }
}
