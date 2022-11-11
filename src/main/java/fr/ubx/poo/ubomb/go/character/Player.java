/*
 * Copyright (c) 2020. Laurent Réveillère
 */

package fr.ubx.poo.ubomb.go.character;

import fr.ubx.poo.ubomb.game.BombParameter;
import fr.ubx.poo.ubomb.game.Direction;
import fr.ubx.poo.ubomb.game.Game;
import fr.ubx.poo.ubomb.game.Position;
import fr.ubx.poo.ubomb.go.GameObject;
import fr.ubx.poo.ubomb.go.Movable;
import fr.ubx.poo.ubomb.go.TakeVisitor;
import fr.ubx.poo.ubomb.go.decor.Decor;
import fr.ubx.poo.ubomb.go.decor.bonus.*;

import java.util.ArrayList;
import java.util.List;

public class Player extends GameObject implements Movable, TakeVisitor {
    private Direction direction;
    private boolean moveRequested = false;
    private int lives;

    public Player(Game game, Position position) {
        super(game, position);
        this.direction = Direction.DOWN;
        this.lives = game.configuration().playerLives();
    }


    @Override
    public void take(Key key) {
        System.out.println("Take the key ...");
    }

    @Override
    public void take(Heart heart) {
        lives++;
        System.out.println("Take the heart ...");
    }

    @Override
    public void take(BombRange bombRange) {
        game.bombParameter.updateRange((bombRange.getBonus()?1:-1));
        System.out.println("Take the bomb range " + (bombRange.getBonus()?"increase":"decrease") + " ...");
    }

    @Override
    public void take(BombCount bombCount) {
        game.bombParameter.updateCount((bombCount.getBonus()?1:-1));
        System.out.println("Take the bomb count " + (bombCount.getBonus()?"increase":"decrease") + " ...");
    }

    public void doMove(Direction direction) {
        // This method is called only if the move is possible, do not check again
        Position nextPos = direction.nextPosition(getPosition());
        List<GameObject> next = new ArrayList<>();
        next.add(game.grid().get(nextPos));
        if (next.get(0) instanceof Bonus bonus) {
            bonus.takenBy(this);
            bonus.remove();
        }
        next = game.getGameObjects(nextPos);
        for (GameObject go : next) {
            if (go instanceof Monster){
                updateLives(-1);
            } else if(go instanceof Princess){
                game.setOnPrincess(true);
            }
        }
        setPosition(nextPos);
    }


    public int getLives() {
        return lives;
    }

    public void updateLives(int change){
        if(!game.getListTimer().get(game.nameTimer("Player Invisibility")).isRunning()){
            lives += change;
            game.getListTimer().get(game.nameTimer("Player Invisibility")).start();
            System.out.println("Vous avez reçu "+change+" vies");
        }
    }

    public Direction getDirection() {
        return direction;
    }

    public void requestMove(Direction direction) {
        if (direction != this.direction) {
            this.direction = direction;
            setModified(true);
        }
        moveRequested = true;
    }

    public final boolean canMove(Direction direction) {
        boolean walk =true;
        if(game.grid().get(direction.nextPosition(getPosition()))!=null){
            Decor decor = game.grid().get(direction.nextPosition(getPosition()));
            walk = decor.walkableBy(this);
        }
        return walk && game.grid().inside(direction.nextPosition(getPosition()));

    }

    public void update(long now) {
        if (moveRequested) {
            if (canMove(direction)) {
                doMove(direction);
            }
        }
        moveRequested = false;
    }

    @Override
    public void explode() {
        // TODO
    }
}
