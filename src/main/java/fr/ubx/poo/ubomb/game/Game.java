package fr.ubx.poo.ubomb.game;

import fr.ubx.poo.ubomb.go.GameObject;
import fr.ubx.poo.ubomb.go.character.Bomb;
import fr.ubx.poo.ubomb.go.character.Monster;
import fr.ubx.poo.ubomb.go.character.Player;
import fr.ubx.poo.ubomb.go.character.Princess;

import java.util.*;

public class Game {

    private final Configuration configuration;
    public boolean started=false;

    private Player player;
    private Princess princess;
    final private Set<Monster> monsters;
    private BombParameter bombParameter;
    final private Set<Bomb> bombs;
    private int key=0;
    private Grid grid;

    public Game(Configuration configuration, Grid grid, int level) {
        this.configuration = configuration;
        this.grid = grid;

        bombParameter = new BombParameter(configuration.bombBagCapacity());
        player = new Player(this, configuration.playerPosition());

        monsters = new HashSet<>();
        for(Position pos : grid().monstersSet()){
            monsters.add(new Monster(this,pos,(int)Math.ceil((double)level/2)));
        }

        if(grid.getPrincess() != null){
            princess = new Princess(this, grid.getPrincess());
        } else { princess = null;}
        bombs = new HashSet<>();
    }
    
    // Returns the player, monsters and bomb at a given position
    public Set<GameObject> getGameObjects(Position position) {
        Set<GameObject> gos = new HashSet<>();
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
        for (Bomb b : bombs) {
            if(b.getPosition().equals(position)){
                gos.add(b);
            }
        }
        return gos;
    }

    public Set<GameObject> getAllGameobject(Position p){
        Set<GameObject> list = getGameObjects(p);
        list.add(grid().get(p));
        list.remove(null);
        return list;
    }


    public Configuration configuration() {
        return configuration;
    }

    public Grid grid() {
        return grid;
    }

    public Player player() {
        return this.player;
    }
    public BombParameter bombParameter(){return this.bombParameter;}
    public void setBombParameter(BombParameter bp){this.bombParameter = bp;}
    public Princess princess(){ return this.princess;}
    public Set<Monster> monster(){ return this.monsters;}
    public Set<Bomb> bombs(){ return this.bombs;}
    public int key(){return key;}
    public void addKey(int i){ key+=i;}
    public void setKey(int nb){key = nb;}
}
