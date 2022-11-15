package fr.ubx.poo.ubomb.launcher;

import fr.ubx.poo.ubomb.game.Configuration;

import fr.ubx.poo.ubomb.game.Position;

import java.io.*;
import java.util.List;
import java.util.Properties;

public class World {

    private File fileProperties;

    private int nbLevel;
    private List<String> levels;
    private int playerLevel;

    private Configuration config;


    /*public World(int nbLevel, List<String> levels, Configuration config) {
        this.nbLevel = nbLevel;
        this.levels = levels;
        this.playerLevel = 0;
        this.config = config;
    }*/

    public World(File file){
        this.fileProperties = file;
        buildWorld();
    }

    private void buildWorld(){
        Properties prop = new Properties();;
        try {
            Reader in = new FileReader(fileProperties);
            prop.load(in);
            boolean compression = Boolean.parseBoolean(prop.getProperty("compression"));
            nbLevel = prop.getProperty("levels") != null ? Integer.parseInt(prop.getProperty("levels")) : 1;
            for(int l=1; l < nbLevel+1; l++){
                levels.add(prop.getProperty("level"+l));
            }
            config = new Configuration(
                    new Position(
                            Integer.parseInt(prop.getProperty("player").split("x")[0]),
                            Integer.parseInt(prop.getProperty("player").split("x")[1])
                    ),
                    prop.getProperty("bombBagCapacity") != null ? Integer.parseInt(prop.getProperty("bombBagCapacity")) : 3,
                    prop.getProperty("playerLives") != null ? Integer.parseInt(prop.getProperty("playerLives")) : 5,
                    prop.getProperty("playerInvisibilityTime") != null ? Integer.parseInt(prop.getProperty("playerInvisibilityTime")) : 4000,
                    prop.getProperty("monsterVelocity") != null ? Integer.parseInt(prop.getProperty("monsterVelocity")) : 5,
                    prop.getProperty("monsterInvisibilityTime") != null ? Integer.parseInt(prop.getProperty("monsterInvisibilityTime")) : 1000
            );

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public Configuration getConfig() {
        return config;
    }

    public int getNbLevel() {
        return nbLevel;
    }

    public List<String> getLevels() {
        return levels;
    }

    public int getPlayerLevel() {
        return playerLevel;
    }
}
