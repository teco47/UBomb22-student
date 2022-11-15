/*
 * Copyright (c) 2020. Laurent Réveillère
 */

package fr.ubx.poo.ubomb.go.character;

import fr.ubx.poo.ubomb.game.Game;
import fr.ubx.poo.ubomb.game.Position;
import fr.ubx.poo.ubomb.go.GameObject;

public class Bomb extends GameObject {
    int countdow;

    public Bomb(Game game, Position position) {
        super(game, position);
        countdow = 4;
    }

    public int getCountdow() {
        return countdow;
    }

    public void updateCountdow(int countdow) {
        this.countdow += countdow;
    }

    @Override
    public void explode() {
        // TODO
    }
}
