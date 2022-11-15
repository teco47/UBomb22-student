/*
 * Copyright (c) 2020. Laurent Réveillère
 */

package fr.ubx.poo.ubomb.view;

import fr.ubx.poo.ubomb.go.character.Bomb;
import fr.ubx.poo.ubomb.go.character.Princess;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public class SpriteBomb extends Sprite {

    public SpriteBomb(Pane layer, Bomb bomb) {
        super(layer, null, bomb);
        updateImage();
    }

    @Override
    public void updateImage() {
        Bomb bomb = (Bomb)getGameObject();
        Image image;
        switch (bomb.getCountdow()){
            case 4:
                image = ImageResource.BOMB_3.getImage();
                break;
            case 3:
                image = ImageResource.BOMB_2.getImage();
                break;
            case 2:
                image = ImageResource.BOMB_1.getImage();
                break;
            case 1:
                image = ImageResource.BOMB_0.getImage();
                break;
            case 0:
                image = ImageResource.EXPLOSION.getImage();
                break;
            default:
                image = ImageResource.TREE.getImage();
        }
        setImage(image);
    }
}

