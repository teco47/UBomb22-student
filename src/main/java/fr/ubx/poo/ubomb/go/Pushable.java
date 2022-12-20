/*
 * Copyright (c) 2020. Laurent Réveillère
 */

package fr.ubx.poo.ubomb.go;


import fr.ubx.poo.ubomb.go.character.Player;

public interface Pushable {
    default void pushBy(Player player) {}
}
