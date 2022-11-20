/*
 * Copyright (c) 2020. Laurent Réveillère
 */

package fr.ubx.poo.ubomb.go.character;

import fr.ubx.poo.ubomb.game.Game;
import fr.ubx.poo.ubomb.game.Position;
import fr.ubx.poo.ubomb.go.GameObject;

public class Bomb extends GameObject {
    int countdown;

    public Bomb(Game game, Position position) {
        super(game, position);
        countdown = 4;
    }

    public int getCountdow() {
        return countdown;
    }

    public void updateCountdow(int countdow) {
        this.countdown += countdow;
    }

    @Override
    public void trigger(String flag) {
        switch (flag){
            case "tic-tac":
                if (countdown > 0){
                    countdown--;
                    System.out.println(countdown);
                    setModified(true);
                    game.addTimer(game.configuration().bombStepTimer(),this, "tic-tac");
                }else{
                    explode();
                }
                break;
        }
    }

    @Override
    public void explode() {
        this.remove();
        // TODO
    }
}
