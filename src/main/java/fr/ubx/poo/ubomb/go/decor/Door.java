/*
 * Copyright (c) 2020. Laurent Réveillère
 */

package fr.ubx.poo.ubomb.go.decor;

import fr.ubx.poo.ubomb.game.Game;
import fr.ubx.poo.ubomb.game.Level;
import fr.ubx.poo.ubomb.game.Position;
import fr.ubx.poo.ubomb.go.character.Player;
import fr.ubx.poo.ubomb.launcher.MapLevelFactory;

public class Door extends Decor {
    private boolean open;
    private boolean upStair;

    public Door(Position position, boolean upStair) {
        super(position);
        open = false;
        this.upStair = upStair;
    }
    public Door(Position position , boolean open, boolean upStair) {
        super(position);
        this.open = open;
        this.upStair = upStair;
    }

    public Boolean setOpen(){
        boolean already = open? false : true;
        open = true;
        setModified(true);
        return already;
    }

    public boolean getStatus(){ return open;}

    @Override
    public boolean walkableBy(Player player) {
        return open;
    }

    public boolean isOpen() {return open;}
    public boolean upStair(){return upStair;}
}
