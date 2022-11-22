/*
 * Copyright (c) 2020. Laurent Réveillère
 */

package fr.ubx.poo.ubomb.go.character;

import fr.ubx.poo.ubomb.engine.Timer;
import fr.ubx.poo.ubomb.game.Game;
import fr.ubx.poo.ubomb.game.Position;
import fr.ubx.poo.ubomb.go.GameObject;

public class Bomb extends GameObject {
    int countdown;
    private Timer timerCountdown;

    public Bomb(Game game, Position position) {
        super(game, position);
        countdown = 3;
        timerCountdown = new Timer(game.configuration().bombStepTimer());
        timerCountdown.start();
    }

    public int getCountdown() {return countdown;}

    private void triggerCountdown(){
        if (countdown > 0){
            countdown--;
            setModified(true);
            timerCountdown.start();
        }else{
            explode();
        }
    }
    @Override
    public void trigger(String flag) {
    }
    public void update(long now) {
        timerCountdown.update(now);
        if(!timerCountdown.isRunning()){
            triggerCountdown();
        }
    }

    @Override
    public void explode() {
        this.remove();
        // TODO
    }
}
