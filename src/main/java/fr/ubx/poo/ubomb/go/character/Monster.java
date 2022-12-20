/*
 * Copyright (c) 2020. Laurent Réveillère
 */

package fr.ubx.poo.ubomb.go.character;

import fr.ubx.poo.ubomb.engine.Timer;
import fr.ubx.poo.ubomb.game.Direction;
import fr.ubx.poo.ubomb.game.Game;
import fr.ubx.poo.ubomb.game.Position;
import fr.ubx.poo.ubomb.go.GameObject;

import java.util.Random;
import java.util.Set;

public class Monster extends Character{

    private Random rand = new Random();
    private boolean loopMove = false;
    private Timer timerMove;

    public Monster(Game game, Position position, int lives) {
        super(game, position,lives,game.configuration().monsterInvisibilityTime());
        timerMove = new Timer(1000);
        timerMove.start();
    }

    public void moveMonster(){
        if(loopMove){
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
    }

    @Override
    public final boolean canMove(Direction direction) {
        boolean walk;
        Position nextPos = direction.nextPosition(getPosition());
        walk = game.grid().inside(nextPos)?true: false;
        if (walk){
            Set<GameObject> setObject = game.getAllGameobject(nextPos);
            walk = !setObject.stream().anyMatch(obj -> !obj.walkableBy(this));
        }
        return walk ;
    }

    public void doMove(Direction direction) {
        // This method is called only if the move is possible, do not check again
        setPosition(direction.nextPosition(getPosition()));
        loopMove = false;
        timerMove.start();
    }

    @Override
    public void update(long now) {
        super.update(now);
        timerMove.update(now);
        if(!timerMove.isRunning()){
            loopMove =true;
        }
        moveMonster();
    }

    public void setTimerMove(int duration){ timerMove = new Timer(duration);}
}
