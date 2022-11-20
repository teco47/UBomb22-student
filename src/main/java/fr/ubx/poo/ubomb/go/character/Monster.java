/*
 * Copyright (c) 2020. Laurent Réveillère
 */

package fr.ubx.poo.ubomb.go.character;

import fr.ubx.poo.ubomb.game.Direction;
import fr.ubx.poo.ubomb.game.Game;
import fr.ubx.poo.ubomb.game.Position;
import fr.ubx.poo.ubomb.go.GameObject;
import fr.ubx.poo.ubomb.go.decor.Decor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Monster extends Character{

    private boolean triggerMoving = false;

    public Monster(Game game, Position position, int lives) {
        super(game, position,lives);
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
        triggerMoving = false;
        Random rand = new Random();
        game.addTimer(game.configuration().monsterVelocity()*(int)(500* rand.nextDouble(0.75,1.25)),this, "Monster Velocity");
    }

    @Override
    public final boolean canMove(Direction direction) {
        boolean walk = true;
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

    @Override
    public void update(long now) {
        super.update(now);
    }

    @Override
    public void trigger(String flag) {
        switch (flag){
            case "Monster Velocity":
                triggerMoving =true;
                break;
            case "Monster Invisibility":
                setInvisibility(false);
                break;
        }
    }

    public void moveMonster(){
        if(triggerMoving){
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

    @Override
    public void explode() {
        // TODO
    }
}
