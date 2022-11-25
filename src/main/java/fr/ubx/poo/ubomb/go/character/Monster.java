/*
 * Copyright (c) 2020. Laurent Réveillère
 */

package fr.ubx.poo.ubomb.go.character;

import fr.ubx.poo.ubomb.engine.Timer;
import fr.ubx.poo.ubomb.game.Direction;
import fr.ubx.poo.ubomb.game.Game;
import fr.ubx.poo.ubomb.game.Position;
import fr.ubx.poo.ubomb.go.GameObject;
import fr.ubx.poo.ubomb.go.decor.Decor;

import java.util.Random;
import java.util.Set;

public class Monster extends Character{

    private boolean loopMove = false;
    private Timer timerMove;


    public Monster(Game game, Position position, int lives) {
        super(game, position,lives,game.configuration().monsterInvisibilityTime());
        timerMove = new Timer(1000);
        timerMove.start();
        System.out.println(timerMove.isRunning());
    }

    @Override
    public final boolean canMove(Direction direction) {
        boolean walk = true;
        if(game.grid().get(direction.nextPosition(getPosition()))!=null){
            Decor decor = game.grid().get(direction.nextPosition(getPosition()));
            walk = decor.walkableBy(this);
        } else {

            Position nextPos = direction.nextPosition(getPosition());
            Set<GameObject> next = game.getGameObjects(nextPos);
            for (GameObject go : next) {
                if (go instanceof Princess ){
                    walk = false;
                }
            }
        }
        return walk && game.grid().inside(direction.nextPosition(getPosition()));
    }

    public void moveMonster(){
        if(loopMove){
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
    }

    public void doMove(Direction direction) {
        // This method is called only if the move is possible, do not check again
        Position nextPos = direction.nextPosition(getPosition());
        Set<GameObject> next = game.getGameObjects(nextPos);
        for (GameObject go : next) {
            if (go instanceof Player player){
                player.updateLives(-1);
            }
        }
        setPosition(nextPos);
        loopMove = false;
        timerMove.start();
    }

    @Override
    public void update(long now) {
        super.update(now);
        timerMove.update(now);//update all timer
        if(!timerMove.isRunning()){
            loopMove =true;
        }
        moveMonster();
    }

    @Override
    public void trigger(String flag) {
    }
}
