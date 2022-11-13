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
    final private List<Monster> monsters;
    public BombParameter bombParameter;

    private final Grid grid;
    private boolean onPrincess;

    // List of Timer
    // 0 - Invisibility of Player
    private final List<Timer> listTimer;
    private final Map<String, Integer> nameTimer;

    public Game(Configuration configuration, Grid grid) {
        this.configuration = configuration;
        bombParameter = new BombParameter(this.configuration().bombBagCapacity());
        this.grid = grid;
        player = new Player(this, configuration.playerPosition());
        monsters = new ArrayList<>();
        for(Position pos : grid().monstersSet()){
            monsters.add(new Monster(this,pos,monsters.size()));
        }
        if(grid.getPrincess() != null){
            princess = new Princess(this, grid.getPrincess());
        } else { princess = null;}
        onPrincess = false;

        listTimer = new ArrayList<>();
        nameTimer = new HashMap<>();
        instantiateTimer();
    }

    private void instantiateTimer(){
        int nbTimer = 0;
        listTimer.add(new Timer(configuration.playerInvisibilityTime()));
        nameTimer.put("Player Invisibility",nbTimer++);

        for(int m=0; m< monsters.size(); m++){
            Random rand = new Random();
            listTimer.add(new Timer(configuration.monsterVelocity()*(int)(500* rand.nextDouble(0.75,1.25))));
            nameTimer.put("Monster Velocity Timer "+m,nbTimer++);
        }

        listTimer.add(new Timer(1000));
        nameTimer.put("bomb step",nbTimer++);
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

    public Princess princess(){ return this.princess;}
    public List<Monster> monster(){ return this.monsters;}

    public List<Timer> getListTimer(){ return this.listTimer;}
    public int nameTimer(String name){
        return nameTimer.get(name);
    }
    public void setOnPrincess(boolean on){
        onPrincess = on;
    }
    public boolean getOnPrincess() { return onPrincess;}
}
