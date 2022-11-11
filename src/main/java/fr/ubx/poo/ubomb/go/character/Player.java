/*
 * Copyright (c) 2020. Laurent Réveillère
 */

package fr.ubx.poo.ubomb.go.character;

import fr.ubx.poo.ubomb.game.Direction;
import fr.ubx.poo.ubomb.game.Game;
import fr.ubx.poo.ubomb.game.Position;
import fr.ubx.poo.ubomb.go.GameObject;
import fr.ubx.poo.ubomb.go.Movable;
import fr.ubx.poo.ubomb.go.TakeVisitor;
import fr.ubx.poo.ubomb.go.decor.Decor;
import fr.ubx.poo.ubomb.go.decor.bonus.*;

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
    public void take(BombRange bombBonus) {
        System.out.println("Take the bomb range " + (bombBonus.getBonus()?"increase":"decrease") + " ...");
    }

    @Override
    public void take(BombCount bombCount) {
        System.out.println("Take the bomb count " + (bombCount.getBonus()?"increase":"decrease") + " ...");
    }

    public void doMove(Direction direction) {
        // This method is called only if the move is possible, do not check again
        Position nextPos = direction.nextPosition(getPosition());
        GameObject next = game.grid().get(nextPos);
        if (next instanceof Bonus bonus) {
                bonus.takenBy(this);
        }
        setPosition(nextPos);
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
