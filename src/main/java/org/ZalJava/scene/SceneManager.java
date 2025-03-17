package org.ZalJava.scene;

import org.ZalJava.core.ResourceManager;
import org.ZalJava.core.Shader;
import org.ZalJava.core.Texture;
import org.joml.Vector3f;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class SceneManager {
    private static String scenePath = null;
    private static final HashMap<String, Scene> scenes = new HashMap<>();
    private static Scene currentScene;

    public SceneManager() {

    }
    public static void init(){
        String path = "src/main/resources/scenes";
        File file = new File(path);
        scenePath = file.getAbsolutePath();

        readScene();
        currentScene = scenes.get("test");
    }

    private static void readScene(){
        File[] files = null;
        try{
            File f = new File(scenePath);
            files = f.listFiles();
        }catch (NullPointerException e){
            System.err.println("Cant open scene folder");
            System.err.println(e.getMessage());
            return;
        }

        if(files == null){
            System.err.println("Folder is empty");
            return;
        }

        for(File fileEntry : files){
            String name = fileEntry.getName().split("\\.")[0];
            Scene scene = new Scene(name);
            scenes.put(name, scene);
            createEntitiesFromFile(fileEntry, scenes.get(name));
        }
    }

    private static void createEntitiesFromFile(File file, Scene scene){
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.err.println("Cant open file");
            System.err.println(e.getMessage());
            return;
        }
        while(scanner.hasNextLine()){
            String line = scanner.nextLine();
            String[] parts = line.split(" ");
            System.out.println(Arrays.toString(parts));
            String entityName = parts[0];
            String shaderName = parts[1];
            String textureName = parts[2];
            float x = Float.parseFloat(parts[3]);
            float y = Float.parseFloat(parts[4]);
            float z = Float.parseFloat(parts[5]);
            float r = Float.parseFloat(parts[6]);
            float g = Float.parseFloat(parts[7]);
            float b = Float.parseFloat(parts[8]);
            float scale = Float.parseFloat(parts[9]);
            Shader shader = null;
            Texture texture = null;
            Vector3f position = new Vector3f(x, y, z);
            Vector3f color = new Vector3f(r, g, b);
            if(!shaderName.equals("null")){
                shader = ResourceManager.getShader(shaderName);
            }
            if(!textureName.equals("null")){
                texture = ResourceManager.getTexture(textureName);
            }
            if(entityName.equals("Cube")){
                Cube c =new Cube(scene.getPath(), texture, shader, position, color);
                scene.addEntity(c);
            }
            if(entityName.equals("Player")){
                Player p = new Player(scene.getPath(), position);
                scene.addEntity(p);
            }
        }
    }

    public static void saveCurrentScene(){
        PrintWriter printWriter = null;
        System.out.println("Saving scene " + scenePath);
        try{
            printWriter = new PrintWriter(scenePath + "/" + currentScene.getPath() + ".scene", StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.err.println("Could not save current scene to " + currentScene.getPath());
            System.err.println(e.getMessage());
            return;
        }
        for(int i = 0; i < currentScene.getEntities().size(); i++){
            printWriter.println(currentScene.getEntities().get(i));
        }
//        for(Entity entity : currentScene.getEntities()){
//            System.out.println(entity.toString());
//            printWriter.println(entity.toString());
//        }
        printWriter.close();

    }

    public static HashMap<String, Scene> getScenes() {
        return scenes;
    }

    public static Scene getCurrentScene() {
        return currentScene;
    }
    public static Scene getScene(String sceneHandle) {
        return scenes.get(sceneHandle);
    }

    public static void addScene(String name, Scene scene) {
        scenes.put(name, scene);
    }

    public static void setCurrentScene(String name) {
        if(!scenes.containsKey(name)){
            System.err.println("Could not set current scene to " + name);
        }
        currentScene = scenes.get(name);
    }
}
