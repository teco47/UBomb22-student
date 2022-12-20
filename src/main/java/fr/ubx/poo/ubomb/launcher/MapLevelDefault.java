package fr.ubx.poo.ubomb.launcher;

import static fr.ubx.poo.ubomb.launcher.Entity.*;

public class MapLevelDefault extends MapLevel {
    private final static Entity[][] level1 = {
            {Empty, Heart, Box, DoorPrevOpened, Empty, DoorNextClosed, DoorNextOpened, Monster,Empty, Empty, Empty, Empty},
            {Empty, Empty, Stone, Empty, Stone, Empty, Stone, Stone, Stone, Stone, Stone, Empty},
            {Empty, Empty, Empty, Empty, Stone, Empty, Stone, Empty, Empty, Stone, Monster, Stone},
            {Empty, Empty, Empty, Empty, Stone, Empty, Stone, Empty, Empty, Stone, Stone, Empty},
            {Empty, Empty, Empty, Empty, Stone, Stone, Stone, Empty, Empty, Empty, Empty, Empty},
            {Empty, Empty, Empty, Empty, Empty, Empty, Empty, Key, Empty, Stone, Empty, Empty},
            {Empty, Tree, Empty, Tree, Empty, Empty, BombRangeInc, BombRangeDec, Empty, Stone, Empty, Empty},
            {Empty, Empty, Empty, Tree, Empty, Empty, BombRangeInc, BombRangeDec, Empty, Stone, Empty, Empty},
            {Empty, Tree, Tree, Tree, Empty, Empty, Empty, Empty, Empty, Stone, Empty, Empty},
            {Empty, Empty, Empty, Empty, Empty, Empty, Box, Empty, Empty, Empty, Empty, Empty},
            {Stone, Stone, Stone, Stone, Stone, Empty, Empty, Empty, Stone, Stone, Empty, Stone},
            {Empty, Empty, Empty, BombNumberDec, BombNumberInc, Empty, Empty, Empty, Empty, Empty, Empty, Empty},
            {Empty, Empty, Empty, BombNumberDec, BombNumberInc, Empty, Empty, Empty, Empty, Empty, Empty, Princess}
    };
    private final static int width = 12;
    private final static int height = 13;

    public MapLevelDefault() {
        super(width, height);
        for (int i = 0; i < width; i++)
            for (int j = 0; j < height; j++)
                set(i, j, level1[j][i]);
    }
}
