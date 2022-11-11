/*
 * Copyright (c) 2020. Laurent Réveillère
 */

package fr.ubx.poo.ubomb.go.decor;

import fr.ubx.poo.ubomb.game.Position;

public class Door extends Decor {
    private boolean open;
    public Door(Position position) {
        super(position);
        open = false;
    }
    public Door(Position position , boolean open) {
        super(position);
        this.open = open;
    }

    public Boolean setOpen(){
        boolean already = open? false : true;
        open = true;
        return already;
    }

    public boolean getStatus(){ return open;}
}
