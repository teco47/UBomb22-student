package fr.ubx.poo.ubomb.go.character;

import fr.ubx.poo.ubomb.game.Direction;
import fr.ubx.poo.ubomb.game.Game;
import fr.ubx.poo.ubomb.game.Position;
import fr.ubx.poo.ubomb.go.GameObject;
import fr.ubx.poo.ubomb.go.Movable;
import fr.ubx.poo.ubomb.go.TakeVisitor;

public abstract class Character extends GameObject  implements Movable, TakeVisitor {

    private Direction direction;
    private boolean moveRequested = false;
    private int lives;
    private boolean isInvisibility = false;

    public Character(Game game, Position position, int lives) {
        super(game, position);
        this.direction = Direction.DOWN;
        this.lives = lives;
    }

    public Character(Position position) {
        super(position);
    }

    public int getLives(){
        return lives;
    }
    public void setLives(int lives){
        this.lives = lives;
    }

    public Direction getDirection() {
        return direction;
    }

    public boolean getIsInvisibility() {
        return isInvisibility;
    }

    public void setInvisibility(boolean invisibility) {
        isInvisibility = invisibility;
    }

    public void requestMove(Direction direction) {
        if (direction != this.direction) {
            this.direction = direction;
            setModified(true);
        }
        moveRequested = true;
    }

    public abstract void doMove(Direction direction);
    public abstract boolean canMove(Direction direction);

    public void update(long now) {
        if (moveRequested) {
            if (canMove(direction)) {
                doMove(direction);
            }
        }
        moveRequested = false;
    }
}
