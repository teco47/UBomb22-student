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
import fr.ubx.poo.ubomb.go.decor.Door;
import fr.ubx.poo.ubomb.go.decor.bonus.*;

import java.util.ArrayList;
import java.util.List;

public class Player extends Character{

    public Player(Game game, Position position) {
        super(game, position, game.configuration().playerLives());
    }

    @Override
    public void take(Key key) {
        System.out.println("Take the key ...");
        game.key(1);
    }

    @Override
    public void take(Heart heart) {
        updateLives(1);
        System.out.println("Take the heart ...");
    }

    @Override
    public void take(BombRange bombRange) {
        game.bombParameter().updateRange((bombRange.getBonus()?1:-1));
        System.out.println("Take the bomb range " + (bombRange.getBonus()?"increase":"decrease") + " ...");
    }

    @Override
    public void take(BombCount bombCount) {
        game.bombParameter().updateCount((bombCount.getBonus()?1:-1));
        System.out.println("Take the bomb count " + (bombCount.getBonus()?"increase":"decrease") + " ...");
    }

    @Override
    public void doMove(Direction direction) {
        // This method is called only if the move is possible, do not check again
        Position nextPos = direction.nextPosition(getPosition());
        List<GameObject> next = game.getGameObjects(nextPos);
        next.add(game.grid().get(nextPos));
        for (GameObject go : next) {
            if (go instanceof Bonus bonus) {
                bonus.takenBy(this);
                bonus.remove();
            } else if (go instanceof Door){
                ((Door) go).travel(this.game);
            } else if (go instanceof Monster){
                updateLives(-1);
            } else if(go instanceof Princess){
                game.setOnPrincess(true);
            }
        }
        setPosition(nextPos);
    }

    public void updateLives(int change){
        if(change > 0){
            super.setLives(getLives()+change);
        } else {
            if(!getIsInvisibility()){
                setLives(getLives()+change);
                setInvisibility(true);
                game.addTimer(game.configuration().playerInvisibilityTime(),this,"Player Invisibility");
            }
        }
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
        super.update(now);
    }

    @Override
    public void trigger(String flag) {
        switch (flag){
            case "Player Invisibility":
                setInvisibility(false);
                break;
            default:
        }
    }

    @Override
    public void explode() {
        // TODO
    }
}
