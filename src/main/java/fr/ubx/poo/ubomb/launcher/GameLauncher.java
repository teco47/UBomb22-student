package fr.ubx.poo.ubomb.launcher;

import fr.ubx.poo.ubomb.game.Configuration;
import fr.ubx.poo.ubomb.game.Game;
import fr.ubx.poo.ubomb.game.Level;
import fr.ubx.poo.ubomb.game.Position;

public class GameLauncher {

    public static Game load() {
        Configuration configuration = new Configuration(new Position(0, 0), 3, 1000,5, 4000, 5, 1000);
        return new Game(configuration, null, new Level(new MapLevelDefault()));
    }

    /*public static Game load(World world){
        return new Game(world.getConfig(), this, new Level());
    }*/

}
