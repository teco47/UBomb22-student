package fr.ubx.poo.ubomb.game;

import fr.ubx.poo.ubomb.engine.Timer;
import fr.ubx.poo.ubomb.go.GameObject;
import fr.ubx.poo.ubomb.go.character.Bomb;
import fr.ubx.poo.ubomb.go.character.Monster;
import fr.ubx.poo.ubomb.go.character.Player;
import fr.ubx.poo.ubomb.go.character.Princess;
import fr.ubx.poo.ubomb.launcher.World;
import javafx.geometry.Pos;

import java.util.*;

public class Game {

    private final Configuration configuration;
    private final World world;
    private final Player player;
    private final Princess princess;
    final private Set<Monster> monsters;
    private BombParameter bombParameter;
    private int key=0;
    private final Grid grid;
    private boolean onPrincess;

    private final Set<Timer> timerSet;

    public Game(Configuration configuration, World world, Grid grid) {
        this.configuration = configuration;
        this.world = world;

        bombParameter = new BombParameter(this.configuration().bombBagCapacity());

        this.grid = grid;
        player = new Player(this, configuration.playerPosition());
        monsters = new HashSet<>();
        for(Position pos : grid().monstersSet()){
            monsters.add(new Monster(this,pos,1));
        }
        if(grid.getPrincess() != null){
            princess = new Princess(this, grid.getPrincess());
        } else { princess = null;}
        onPrincess = false;
        timerSet = new HashSet<>();
    }

    public Configuration configuration() {
        return configuration;
    }

    // Returns the player, monsters and bomb at a given position
    public List<GameObject> getGameObjects(Position position) {
        List<GameObject> gos = new LinkedList<>();
        if (player().getPosition().equals(position))
            gos.add(player);
        if(princess!=null && princess().getPosition().equals(position)){
            gos.add(princess);
        }

        for (Monster m : monsters) {
            if(m.getPosition().equals(position)){
                gos.add(m);
            }
        }
        return gos;
    }

    public void removeBonus(Position position){
        grid.remove(position);
    }

    public Grid grid() {
        return grid;
    }

    public Player player() {
        return this.player;
    }
    public BombParameter bombParameter(){return this.bombParameter;}
    public Princess princess(){ return this.princess;}
    public Set<Monster> monster(){ return this.monsters;}

    public Set<Timer> timerSet(){return this.timerSet;}
    /*public void addTimer(long duration, GameObject go, String name){
        Timer t = new Timer(duration,go, name);
        timerSet.add(t);
        t.start();
    }*/

    public void setOnPrincess(boolean on){
        onPrincess = on;
    }
    public int key(){return key;}
    public void key(int i){ key+=i;}
    public boolean getOnPrincess() { return onPrincess;}
}
