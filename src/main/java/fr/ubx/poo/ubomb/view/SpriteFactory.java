/*
 * Copyright (c) 2020. Laurent Réveillère
 */

package fr.ubx.poo.ubomb.view;

import fr.ubx.poo.ubomb.go.GameObject;
import fr.ubx.poo.ubomb.go.decor.bonus.*;
import fr.ubx.poo.ubomb.go.decor.*;
import javafx.scene.layout.Pane;

import static fr.ubx.poo.ubomb.view.ImageResource.*;


public final class SpriteFactory {

    public static Sprite create(Pane layer, GameObject gameObject) {
        if (gameObject instanceof Stone)
            return new Sprite(layer, STONE.getImage(), gameObject);
        if (gameObject instanceof Tree)
            return new Sprite(layer, TREE.getImage(), gameObject);
        if (gameObject instanceof Box)
            return new Sprite(layer, BOX.getImage(), gameObject);
        if (gameObject instanceof Door){
            if (!((Door) gameObject).getStatus()){//closed
                return new Sprite(layer, DOOR_CLOSED.getImage(), gameObject);
            }else{ //open
                return new Sprite(layer, DOOR_OPENED.getImage(), gameObject);
            }
        }
        if (gameObject instanceof BombRange){
            if (((BombRange) gameObject).getBonus()){//increase
                return new Sprite(layer, BONUS_BOMB_RANGE_INC.getImage(), gameObject);
            }else{ //decrease
                return new Sprite(layer, BONUS_BOMB_RANGE_DEC.getImage(), gameObject);
            }
        }
        if (gameObject instanceof BombCount){
            if (((BombCount) gameObject).getBonus()){//increase
                return new Sprite(layer, BONUS_BOMB_NB_INC.getImage(), gameObject);
            }else{ //decrease
                return new Sprite(layer, BONUS_BOMB_NB_DEC.getImage(), gameObject);
            }
        }
        if (gameObject instanceof Key)
            return new Sprite(layer, KEY.getImage(), gameObject);
        if (gameObject instanceof Heart)
            return new Sprite(layer, HEART.getImage(), gameObject);
        throw new RuntimeException("Unsupported sprite for decor " + gameObject);
    }
}
