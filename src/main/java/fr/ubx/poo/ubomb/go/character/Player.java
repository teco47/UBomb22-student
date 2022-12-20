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
import fr.ubx.poo.ubomb.go.decor.Box;

import fr.ubx.poo.ubomb.go.decor.Decor;
import fr.ubx.poo.ubomb.go.decor.Door;
import fr.ubx.poo.ubomb.go.decor.Tree;
import fr.ubx.poo.ubomb.go.decor.bonus.*;
import java.util.Set;


public class Player extends Character{

    //boolean moveObject;


    public Player(Game game, Position position) {
        super(game, position, game.configuration().playerLives(),game.configuration().playerInvisibilityTime());
    }
    
    @Override
    public void take(Key key) {
        System.out.println("Take the key ...");
        game.addKey(1);
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
        game.bombParameter().updateMaxCount((bombCount.getBonus()?1:-1));
        System.out.println("Take the bomb count " + (bombCount.getBonus()?"increase":"decrease") + " ...");
    }

    public final boolean canMove(Direction direction) {
        boolean walk = true;
        Position newPosition = direction.nextPosition(getPosition());
        //GameObject object = game.grid().get(newPosition);

        Set<GameObject> setObject = game.getGameObjects(newPosition);
        setObject.add(game.grid().get(newPosition));
        setObject.remove(null);
        walk = !setObject.stream().anyMatch(obj -> !obj.walkableBy(this));
        setObject.forEach( (object) -> object.pushBy(this));
        return walk && game.grid().inside(direction.nextPosition(getPosition()));

    }

    @Override
    public void doMove(Direction direction) {
        // This method is called only if the move is possible, do not check again
        Position nextPos = direction.nextPosition(getPosition());
        Set<GameObject> next = game.getGameObjects(nextPos);
        next.add(game.grid().get(nextPos));
        for (GameObject go : next) {
            if (go instanceof Bonus bonus) {
                bonus.takenBy(this);
                bonus.remove();
            } else if (go instanceof Monster){
                updateLives(-1);
            } else if(go instanceof Princess){
                game.setOnPrincess(true);
            }
        }
        setPosition(nextPos);
    }

    public void push(Box box){
        Position newPosition = getDirection().nextPosition(box.getPosition());
        Set<GameObject> setObject = game.getGameObjects(newPosition);
        setObject.add(game.grid().get(newPosition));
        setObject.remove(null);
        if (setObject.size()==0){
            game.grid().remove(box.getPosition());
            game.grid().set(newPosition, box);
            box.setPosition(newPosition);
        }
    }

    public void push (Bomb bomb){
        Position newPosition = getDirection().nextPosition(bomb.getPosition(),bomb.getRange());
        if (game.grid().inside(newPosition)){
            bomb.setPosition(newPosition);
        }else{
            Direction oppositeDirection = getDirection().opposite();
            while( !game.grid().inside(newPosition)){
                newPosition = oppositeDirection.nextPosition(newPosition);
                System.out.println(newPosition);
            }
            bomb.setPosition(newPosition);
        }

    }

    public void update(long now) {
        super.update(now);
    }

    public void openDoor(){
        if(game.key()>0){
            if(game.grid().get(this.getDirection().nextPosition(getPosition())) instanceof Door){
                Door d = ((Door)game.grid().get(this.getDirection().nextPosition(getPosition())));
                if(!d.isOpen()){
                    d.setOpen();
                    game.addKey(-1);
                }
            }
        }
    }
}
