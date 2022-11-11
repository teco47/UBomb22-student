/*
 * Copyright (c) 2020. Laurent Réveillère
 */

package fr.ubx.poo.ubomb.view;

import fr.ubx.poo.ubomb.go.character.Monster;
import fr.ubx.poo.ubomb.go.character.Princess;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public class SpritePrincess extends Sprite {

    public SpritePrincess(Pane layer, Princess princess) {
        super(layer, null, princess);
        updateImage();
    }

    @Override
    public void updateImage() {
        Princess princess = (Princess) getGameObject();
        Image image = ImageResource.PRINCESS.getImage();
        setImage(image);
    }
}

