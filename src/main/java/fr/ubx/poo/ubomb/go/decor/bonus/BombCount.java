/*
 * Copyright (c) 2020. Laurent Réveillère
 */

package fr.ubx.poo.ubomb.go.decor.bonus;

import fr.ubx.poo.ubomb.game.Position;
import fr.ubx.poo.ubomb.go.character.Player;

public class BombCount extends Bonus {
    private Boolean increase ;
    public BombCount(Position position, boolean increase) {
        super(position);
        this.increase = increase;
    }

    public boolean getBonus(){return increase;}

    @Override
    public void takenBy(Player player) {player.take(this); }
}
