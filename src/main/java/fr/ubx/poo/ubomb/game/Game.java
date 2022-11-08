package fr.ubx.poo.ubomb.game;

import fr.ubx.poo.ubomb.engine.Timer;
import fr.ubx.poo.ubomb.go.GameObject;
import fr.ubx.poo.ubomb.go.character.Monster;
import fr.ubx.poo.ubomb.go.character.Player;
import fr.ubx.poo.ubomb.go.character.Princess;

import java.util.*;

public class Game {

    private final Configuration configuration;
    private final Player player;
    private final Princess princess;
    private HashSet<Monster> monsters;

    private final Grid grid;

    // List of Timer
    // 0 - Invisibility of Player
    private final List<Timer> listTimer;
    private final Map<String, Integer> nameTimer;

    public Game(Configuration configuration, Grid grid) {
        this.configuration = configuration;
        this.grid = grid;
        player = new Player(this, configuration.playerPosition());
        monsters = new HashSet<>();
        for(Position pos : grid().monstersSet()){
            monsters.add(new Monster(this,pos));
        }
        princess = new Princess(this, grid.getPrincess());

        listTimer = new ArrayList<>();
        nameTimer = new HashMap<>();
        listTimer.add(new Timer(configuration.playerInvisibilityTime()));
        nameTimer.put("Player Invisibility",0);
    }

    public Configuration configuration() {
        return configuration;
    }

    // Returns the player, monsters and bomb at a given position
    public List<GameObject> getGameObjects(Position position) {
        List<GameObject> gos = new LinkedList<>();
        if (player().getPosition().equals(position))
            gos.add(player);
        if(princess().getPosition().equals(position)){
            gos.add(princess);
        }
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

    public  Princess princess(){ return this.princess;}
    public HashSet<Monster> monster(){ return this.monsters;}

    public List<Timer> getListTimer(){ return this.listTimer;}
    public int nameTimer(String name){
        return nameTimer.get(name);
    }
}
