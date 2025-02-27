package fr.ubx.poo.ubomb.go;

import fr.ubx.poo.ubomb.go.decor.Decor;
import fr.ubx.poo.ubomb.go.decor.bonus.*;

// Double dispatch visitor pattern
public interface TakeVisitor {
    // Key
    default void take(Key key) {}
    default void take(BombRange bombBonus) {}

    default void  take(BombCount bombCount){}

    default void  take(Heart heart){}

}
