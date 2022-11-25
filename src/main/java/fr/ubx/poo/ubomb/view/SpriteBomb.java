/*
 * Copyright (c) 2020. Laurent Réveillère
 */

package fr.ubx.poo.ubomb.view;

import fr.ubx.poo.ubomb.go.character.Bomb;
import fr.ubx.poo.ubomb.go.character.Player;
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
        Image image = ImageResourceFactory.getBomb(bomb.getCountdown()).getImage();
        setImage(image);
    }
}

