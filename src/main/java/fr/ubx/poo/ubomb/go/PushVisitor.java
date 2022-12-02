package fr.ubx.poo.ubomb.go;

import fr.ubx.poo.ubomb.go.decor.Box;
import fr.ubx.poo.ubomb.go.decor.bonus.BombCount;
import fr.ubx.poo.ubomb.go.decor.bonus.BombRange;
import fr.ubx.poo.ubomb.go.decor.bonus.Heart;
import fr.ubx.poo.ubomb.go.decor.bonus.Key;

// Double dispatch visitor pattern
public interface PushVisitor {
    // Key
    default void push(Box box) {}

}
