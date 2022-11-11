/*
 * Copyright (c) 2020. Laurent Réveillère
 */

package fr.ubx.poo.ubomb.go.character;

import fr.ubx.poo.ubomb.engine.Timer;
import fr.ubx.poo.ubomb.game.Direction;
import fr.ubx.poo.ubomb.game.Game;
import fr.ubx.poo.ubomb.game.Position;
import fr.ubx.poo.ubomb.go.GameObject;
import fr.ubx.poo.ubomb.go.Movable;
import fr.ubx.poo.ubomb.go.TakeVisitor;
import fr.ubx.poo.ubomb.go.decor.Decor;
import fr.ubx.poo.ubomb.go.decor.bonus.Bonus;
import fr.ubx.poo.ubomb.go.decor.bonus.Key;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Monster extends GameObject implements Movable, TakeVisitor {

    private Direction direction;
    private boolean moveRequested = false;
    private final int lives;
    private final int numMonster;

    public Monster(Game game, Position position, int numMonster) {
        super(game, position);
        this.direction = Direction.DOWN;
        this.lives = 1;
        this.numMonster = numMonster;
    }


    @Override
    public void take(Key key) {
        System.out.println("Take the key ...");
    }

    public void doMove(Direction direction) {
        // This method is called only if the move is possible, do not check again
        Position nextPos = direction.nextPosition(getPosition());
        List<GameObject> next = new ArrayList<>();
        next = game.getGameObjects(nextPos);
        for (GameObject go : next) {
            if (go instanceof Player player){
                player.updateLives(-1);
            }
        }
        setPosition(nextPos);
        game.getListTimer().get(game.nameTimer("Monster Velocity Timer "+numMonster)).start();
    }


    public int getLives() {
        return lives;
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
        } else {
            List<GameObject> next;
            Position nextPos = direction.nextPosition(getPosition());
            next = game.getGameObjects(nextPos);
            for (GameObject go : next) {
                if (go instanceof Princess p){
                    walk = false;
                }
            }
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

    public void moveMonster(){
        Random rand = new Random();
        int direction = rand.nextInt(4);
        switch (direction){
            case 0:
                requestMove(Direction.UP);
                break;
            case 1:
                requestMove(Direction.DOWN);
                break;
            case 2:
                requestMove(Direction.LEFT);
                break;
            case 3:
                requestMove(Direction.RIGHT);
                break;
        }

    }

    @Override
    public void explode() {
        // TODO
    }
}
