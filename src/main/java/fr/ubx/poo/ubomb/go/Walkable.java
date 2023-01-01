package fr.ubx.poo.ubomb.go;

import fr.ubx.poo.ubomb.go.character.Monster;
import fr.ubx.poo.ubomb.go.character.Player;

public interface Walkable {
    default boolean walkableBy(Player player) {return true;}
    default boolean walkableBy(Monster monster) {
        return false;
    }
}
