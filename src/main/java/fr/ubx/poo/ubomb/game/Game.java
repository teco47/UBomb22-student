package fr.ubx.poo.ubomb.game;

import fr.ubx.poo.ubomb.engine.Timer;
import fr.ubx.poo.ubomb.go.GameObject;
import fr.ubx.poo.ubomb.go.character.Bomb;
import fr.ubx.poo.ubomb.go.character.Monster;
import fr.ubx.poo.ubomb.go.character.Player;
import fr.ubx.poo.ubomb.go.character.Princess;
import fr.ubx.poo.ubomb.launcher.MapLevelFactory;
import fr.ubx.poo.ubomb.launcher.World;

import java.util.*;

public class Game {

    private final Configuration configuration;
    private final World world;
    private Player player;
    private Princess princess;
    final private List<Monster> monsters;
    private BombParameter bombParameter;

    private int key=0;

    private Grid grid;
    private boolean onPrincess;
    private final Set<Timer> timerSet;
    private boolean onTravel;

    public Game(Configuration configuration, World world, Grid grid) {
        this.configuration = configuration;
        this.world = world;
        this.grid = grid;

        bombParameter = new BombParameter(this.configuration().bombBagCapacity());
        player = new Player(this, configuration.playerPosition());
        monsters = new ArrayList<>();
        for(Position pos : grid.monstersSet()){
            monsters.add(new Monster(this,pos,monsters.size()));
        }
        if(grid.getPrincess() != null){
            princess = new Princess(this, grid.getPrincess());
        } else { princess = null;}
        onPrincess = false;
        timerSet = new HashSet<>();
    }

    private void reInitCharacter(){
        //player.setPosition(configuration.playerPosition());
        /*player.updateLives(-5);
        player.setPosition(new Position(0,0));*/
        int remainingLives = player.getLives();
        player = new Player(this, new Position(0,0));
        player.setLives(remainingLives);
        monsters.clear();
        for(Position pos : grid.monstersSet()){
            monsters.add(new Monster(this,pos,monsters.size()));
        }
        if(grid.getPrincess() != null){
            princess = new Princess(this, grid.getPrincess());
        } else { princess = null;}
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
    public List<Monster> monster(){ return this.monsters;}

    public Set<Timer> getTimerSet(){return timerSet;}
    public void addTimer(long duration, GameObject go, String name){
        Timer t = new Timer(duration,go, name);
        timerSet.add(t);
        t.start();
    }
    public void setOnPrincess(boolean on){
        onPrincess = on;
    }
    public int key(){return key;}
    public void key(int i){ key+=i;}
    public boolean getOnPrincess() { return onPrincess;}

    public void travelTo(boolean upStair){
        int toLevel = world.getPlayerLevel() + (upStair ? 1:-1);
        MapLevelFactory nextMap = new MapLevelFactory(world.getLevel(toLevel));
        world.setPlayerLevel(toLevel);
        grid = new Level(nextMap.getMap());

        reInitCharacter();
        onTravel = true;
    }
    public boolean isOnTravel(){return onTravel;}

    public void endTravel(){ onTravel = false;}
}
