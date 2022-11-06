package fr.ubx.poo.ubomb.launcher;

import fr.ubx.poo.ubomb.game.*;

import java.io.File;

public class GameLauncher {

    public static Game load() {
        Configuration configuration = new Configuration(new Position(0, 0), 3, 5, 4000, 5, 1000);
        Game game = new Game(configuration, new Level(new MapLevelDefault()));
        return game;
    }

}
