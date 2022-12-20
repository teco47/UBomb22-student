/*
 * Copyright (c) 2020. Laurent Réveillère
 */

package fr.ubx.poo.ubomb.view;

import fr.ubx.poo.ubomb.go.character.Monster;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class SpriteMonster extends Sprite {

    public SpriteMonster(Pane layer, Monster monster) {
        super(layer, null, monster);
        updateImage();
    }

    @Override
    public void updateImage() {
        Monster monster = (Monster) getGameObject();
        Image image = ImageResourceFactory.getMonster(monster.getDirection()).getImage();

        setImage(image);
    }

    @Override
    protected ImageView changeColor(ImageView imgV){
        Monster monster = (Monster) getGameObject();
        ColorAdjust livesFilters = new ColorAdjust();
        ColorAdjust damageFilter = new ColorAdjust();
        if(monster.getLives() > 1){
            livesFilters.setContrast(0.25* monster.getLives());
            livesFilters.setHue(0.1 * monster.getLives() * 1.5);
            imgV.setEffect(livesFilters);
        }
        if(monster.getIsInvisibility() && monster.stepInvisibility()){
            damageFilter.setBrightness(1);
            imgV.setEffect(damageFilter);
        }
        return imgV;
    }
}

