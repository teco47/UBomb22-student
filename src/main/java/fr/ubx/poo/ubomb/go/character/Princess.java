/*
 * Copyright (c) 2020. Laurent Réveillère
 */

package fr.ubx.poo.ubomb.go.character;

import fr.ubx.poo.ubomb.game.Game;
import fr.ubx.poo.ubomb.game.Position;
import fr.ubx.poo.ubomb.go.GameObject;

public class Princess extends GameObject {

    public Princess(Game game, Position position) {
        super(game, position);
    }

    @Override
    public boolean walkableBy(Monster monster) {return false;}

    @Override
    public boolean explode() {
        return false;
    }
}
