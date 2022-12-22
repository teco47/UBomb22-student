/*
 * Copyright (c) 2020. Laurent Réveillère
 */

package fr.ubx.poo.ubomb.go.character;

import fr.ubx.poo.ubomb.engine.Timer;
import fr.ubx.poo.ubomb.game.Direction;
import fr.ubx.poo.ubomb.game.Game;
import fr.ubx.poo.ubomb.game.Position;
import fr.ubx.poo.ubomb.go.GameObject;

import java.util.*;

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
        List<Direction> available = new ArrayList<>();
        for (Direction dir : Direction.values()){
            if (canMove(dir)){available.add(dir);}
        }
        if (available.size() > 0 ){
            requestMove(Direction.randomSet(available));
        }else{
            requestMove(Direction.random());//simulation of a movement.
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
    }

    @Override
    public void update(long now) {
        super.update(now);
        timerMove.update(now);
        if(!timerMove.isRunning()){
            moveMonster();
            timerMove.start();
        }
    }

    public void setTimerMove(int duration){ timerMove = new Timer(duration);}
}
