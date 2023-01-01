/*
 * Copyright (c) 2020. Laurent Réveillère
 */

package fr.ubx.poo.ubomb.go.character;


import fr.ubx.poo.ubomb.game.Direction;
import fr.ubx.poo.ubomb.game.Game;
import fr.ubx.poo.ubomb.game.Position;
import fr.ubx.poo.ubomb.go.GameObject;
import fr.ubx.poo.ubomb.go.PushVisitor;
import fr.ubx.poo.ubomb.go.decor.Box;
import fr.ubx.poo.ubomb.go.decor.Door;
import fr.ubx.poo.ubomb.go.decor.bonus.*;
import java.util.Set;


public class Player extends Character implements PushVisitor {

    public Player(Game game, Position position) {
        super(game, position, game.configuration().playerLives(),game.configuration().playerInvisibilityTime());
    }
    
    @Override
    public void take(Key key) {game.addKey(1); key.remove();}

    @Override
    public void take(Heart heart) {updateLives(1);heart.remove();}

    @Override
    public void take(BombRange bombRange) {game.bombParameter().updateRange((bombRange.getBonus()?1:-1)); bombRange.remove();}

    @Override
    public void take(BombCount bombCount) {game.bombParameter().updateMaxCount((bombCount.getBonus()?1:-1)); bombCount.remove();}

    public final boolean canMove(Direction direction) {
        boolean walk;
        Position newPosition = direction.nextPosition(getPosition());
        walk = game.grid().inside(newPosition)?true: false;
        if (walk) {
            Set<GameObject> setObject = game.getAllGameobject(newPosition);
            walk = !setObject.stream().anyMatch(obj -> !obj.walkableBy(this));
            setObject.forEach((object) -> object.pushBy(this));
        }
        return walk;

    }

    @Override
    public void doMove(Direction direction) {
        // This method is called only if the move is possible, do not check again
        Position nextPos = direction.nextPosition(getPosition());
        Set<GameObject> next = game.getAllGameobject(nextPos);
        next.forEach(obj -> obj.takenBy(this));
        setPosition(nextPos);
    }
    @Override
    public void push(Box box){
        Position newPosition = getDirection().nextPosition(box.getPosition());
        if (game.grid().inside(newPosition)){
            Set<GameObject> setObject = game.getAllGameobject(newPosition);
            if (setObject.size()==0){
                game.grid().remove(box.getPosition());
                game.grid().set(newPosition, box);
                box.setPosition(newPosition);
            }
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
                if(!d.getStatus()){
                    d.setOpen();
                    game.addKey(-1);
                }
            }
        }
    }
}
