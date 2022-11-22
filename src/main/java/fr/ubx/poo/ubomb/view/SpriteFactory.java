/*
 * Copyright (c) 2020. Laurent Réveillère
 */

package fr.ubx.poo.ubomb.view;

import fr.ubx.poo.ubomb.go.GameObject;
import fr.ubx.poo.ubomb.go.character.Bomb;
import fr.ubx.poo.ubomb.go.decor.bonus.*;
import fr.ubx.poo.ubomb.go.decor.*;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

import static fr.ubx.poo.ubomb.view.ImageResource.*;


public final class SpriteFactory {

    public static Sprite create(Pane layer, GameObject gameObject) {
        return new Sprite(layer,getImage(gameObject),gameObject);
    }

    public static Image getImage(GameObject gameObject) {
        if (gameObject instanceof Stone)
            return STONE.getImage();
        if (gameObject instanceof Tree)
            return  TREE.getImage();
        if (gameObject instanceof Box)
            return BOX.getImage();
        if (gameObject instanceof Door) {
            if (!((Door) gameObject).getStatus()) {//closed
                return  DOOR_CLOSED.getImage();
            } else { //open
                return DOOR_OPENED.getImage();
            }
        }
        if (gameObject instanceof BombRange) {
            if (((BombRange) gameObject).getBonus()) {//increase
                return BONUS_BOMB_RANGE_INC.getImage();
            } else { //decrease
                return BONUS_BOMB_RANGE_DEC.getImage();
            }
        }
        if (gameObject instanceof BombCount) {
            if (((BombCount) gameObject).getBonus()) {//increase
                return BONUS_BOMB_NB_INC.getImage();
            } else { //decrease
                return BONUS_BOMB_NB_DEC.getImage();
            }
        }
        if (gameObject instanceof Key)
            return KEY.getImage();
        if (gameObject instanceof Heart)
            return HEART.getImage();
        if (gameObject instanceof Bomb) {
            switch (((Bomb) gameObject).getCountdow()) {
                case 3:
                    return BOMB_3.getImage();
                case 2:
                    return BOMB_2.getImage();
                case 1:
                    return BOMB_1.getImage();
                case 0:
                    return  BOMB_0.getImage();
                default:
                    System.out.println("BOMB_0");
                    return  EXPLOSION.getImage();
            }
        }
        throw new RuntimeException("Unsupported sprite for decor " + gameObject);
    }
}
