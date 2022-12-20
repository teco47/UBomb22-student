/*
 * Copyright (c) 2020. Laurent Réveillère
 */

package fr.ubx.poo.ubomb.view;


import fr.ubx.poo.ubomb.go.character.Player;
import javafx.scene.effect.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class SpritePlayer extends Sprite {

    public SpritePlayer(Pane layer, Player player) {
        super(layer, null, player);
        updateImage();
    }

    @Override
    public void updateImage() {
        Player player = (Player) getGameObject();
        Image image = ImageResourceFactory.getPlayer(player.getDirection()).getImage();
        setImage(image);
    }

    @Override
    protected ImageView changeColor(ImageView imgV){
        Player player = (Player) getGameObject();
        ColorAdjust damageFilter = new ColorAdjust();
        if(player.getIsInvisibility() && player.stepInvisibility()){
            damageFilter.setHue(0);
            damageFilter.setSaturation(0.5);
            damageFilter.setBrightness(-0.10);
            imgV.setEffect(damageFilter);
        }
        return imgV;
    }
}

