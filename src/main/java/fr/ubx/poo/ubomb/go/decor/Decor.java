package fr.ubx.poo.ubomb.go.decor;

import fr.ubx.poo.ubomb.game.Game;
import fr.ubx.poo.ubomb.game.Position;
import fr.ubx.poo.ubomb.go.GameObject;
import fr.ubx.poo.ubomb.go.character.Player;

public abstract class Decor extends GameObject {

    public Decor(Game game, Position position) {
        super(game, position);
    }

    public Decor(Position position) {
        super(position);
    }

    @Override
    public boolean walkableBy(Player player) {
        return false;
    }
}