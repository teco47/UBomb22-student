/*
 * Copyright (c) 2020. Laurent Réveillère
 */

package fr.ubx.poo.ubomb.go.character;

import fr.ubx.poo.ubomb.engine.Timer;
import fr.ubx.poo.ubomb.game.Game;
import fr.ubx.poo.ubomb.game.Position;
import fr.ubx.poo.ubomb.go.GameObject;
import fr.ubx.poo.ubomb.go.Pushable;

public class Bomb extends GameObject {
    int countdown;
    int range;
    private Timer timerCountdown;

    public Bomb(Game game, Position position, int range) {
        super(game, position);
        countdown = 3;
        this.range = range;
        timerCountdown = new Timer(game.configuration().bombStepTimer());
        timerCountdown.start();
    }

    public int getCountdown() {return countdown;}
    public int getRange() {return range;}


    private void triggerCountdown(){
        if (countdown > 0){
            countdown--;
            setModified(true);
            timerCountdown.start();
        }else{
            explode();
        }
    }
    public void update(long now) {
        timerCountdown.update(now);
        if(!timerCountdown.isRunning()){
            triggerCountdown();
        }
    }

    @Override
    public boolean explode() {
        this.remove();
        return false;
        // TODO
    }

    @Override
    public void pushBy(Player player) {
        player.push(this);
    }
}
