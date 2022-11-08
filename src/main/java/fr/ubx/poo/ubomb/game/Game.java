package fr.ubx.poo.ubomb.game;

import fr.ubx.poo.ubomb.go.GameObject;
import fr.ubx.poo.ubomb.go.character.Monster;
import fr.ubx.poo.ubomb.go.character.Player;

import javax.xml.crypto.dsig.keyinfo.KeyValue;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Game {

    private final Configuration configuration;
    private final Player player;
    private HashSet<Monster> monsters;
    private final Grid grid;

    public Game(Configuration configuration, Grid grid) {
        this.configuration = configuration;
        this.grid = grid;
        player = new Player(this, configuration.playerPosition());
        monsters = new HashSet<>();
        for(Position pos : grid().monstersSet()){
            monsters.add(new Monster(this,pos));
        }
    }

    public Configuration configuration() {
        return configuration;
    }

    // Returns the player, monsters and bomb at a given position
    public List<GameObject> getGameObjects(Position position) {
        List<GameObject> gos = new LinkedList<>();
        if (player().getPosition().equals(position))
            gos.add(player);
        for (Monster m : monsters) {
            if(m.getPosition().equals(position)){
                gos.add(m);
            }
        }
        return gos;
    }

    public Grid grid() {
        return grid;
    }

    public Player player() {
        return this.player;
    }

    public HashSet<Monster> monster(){ return this.monsters;}
}
