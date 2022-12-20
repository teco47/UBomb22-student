package fr.ubx.poo.ubomb.go.character;

import fr.ubx.poo.ubomb.engine.Timer;
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

    private Timer timerInvisibility;

    public Character(Game game, Position position, int lives, long invincibilityTime) {
        super(game, position);
        this.direction = Direction.DOWN;
        this.lives = lives;
        this.timerInvisibility = new Timer(invincibilityTime);
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

    public void updateLives(int change){
        if(change > 0){
            setLives(getLives()+change);
        } else {
            if(!getIsInvisibility()){
                setLives(getLives()+change);
                setIsInvisibility(true);
                timerInvisibility.start();
                if(getLives() <=0){
                    remove();
                }
            }
        }

    }

    public Direction getDirection() {
        return direction;
    }

    public boolean getIsInvisibility() {
        return isInvisibility;
    }
    public void setIsInvisibility(boolean invincible) {isInvisibility=invincible;}

    public void requestMove(Direction direction) {
        if (direction != this.direction) {
            this.direction = direction;
            setModified(true);
        }
        if (canMove(direction)) {
            doMove(direction);
        }
    }

    public abstract boolean canMove(Direction direction);

    public abstract void doMove(Direction direction);

    public void update(long now) {
        timerInvisibility.update(now);
        if(isInvisibility && !timerInvisibility.isRunning()){
            isInvisibility = false;
        }
    }

    @Override
    public boolean explode() {
        updateLives(-1);
        return false;
    }
}
