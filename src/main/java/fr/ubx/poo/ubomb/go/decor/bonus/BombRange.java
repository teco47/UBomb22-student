/*
 * Copyright (c) 2020. Laurent Réveillère
 */

package fr.ubx.poo.ubomb.go.decor.bonus;

import fr.ubx.poo.ubomb.game.Position;
import fr.ubx.poo.ubomb.go.character.Player;

public class BombRange extends Bonus {
    private Boolean increase ;
    public BombRange(Position position) {
        super(position);
        increase = true;
    }
    public BombRange(Position position, boolean increase) {
        super(position);
        this.increase = increase;
    }

    public boolean getBonus(){return increase; }


    @Override
    public void takenBy(Player player) {player.take(this); }
}
