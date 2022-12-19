package fr.ubx.poo.ubomb.launcher;

import fr.ubx.poo.ubomb.game.Configuration;
import fr.ubx.poo.ubomb.game.Game;
import fr.ubx.poo.ubomb.game.Level;
import fr.ubx.poo.ubomb.game.Position;

import java.util.ArrayList;

public class GameLauncher {

    public static Game load() {
        Configuration configuration = new Configuration(new Position(0, 0), 3, 1000,5, 4000, 5, 1000);
        return new Game(configuration, new Level(new MapLevelDefault()),1);
    }

    public static ArrayList<Game> load(World world){
        ArrayList<Game> levels = new ArrayList<>();
        for (int l=0; l< world.getNbLevel(); l++){
            MapLevelFactory map = new MapLevelFactory(world.getLevel(l));
            levels.add(new Game(
                    world.getConfig(),
                    new Level(map.getMap()),
                    l+1
            ));
        }
        MapLevelFactory firstMap = new MapLevelFactory(world.getLevel(0));
        return levels;
    }

}
