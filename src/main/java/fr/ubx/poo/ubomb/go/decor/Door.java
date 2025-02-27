/*
 * Copyright (c) 2020. Laurent Réveillère
 */

package fr.ubx.poo.ubomb.go.decor;

import fr.ubx.poo.ubomb.game.Position;
import fr.ubx.poo.ubomb.go.character.Player;

public class Door extends Decor {
    private boolean open;
    private boolean upStair;

    public Door(Position position , boolean open, boolean upStair) {
        super(position);
        this.open = open;
        this.upStair = upStair;
    }

    public Boolean setOpen(){
        boolean already = open;
        open = true;
        setModified(true);
        return already;
    }

    public boolean getStatus(){ return open;}

    @Override
    public boolean walkableBy(Player player) {
        return open;
    }

    public boolean upStair(){return upStair;}
}
