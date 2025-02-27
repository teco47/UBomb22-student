package fr.ubx.poo.ubomb.game;

import fr.ubx.poo.ubomb.go.decor.bonus.*;
import fr.ubx.poo.ubomb.go.decor.*;
import fr.ubx.poo.ubomb.launcher.Entity;
import fr.ubx.poo.ubomb.launcher.MapLevel;

import java.util.*;

public class Level implements Grid {

    private final int width;
    private final int height;

    private final Map<Position, Decor> elements = new HashMap<>();
    private final HashSet<Position> characterMonster = new HashSet<>();
    private final Set<Door> doorSet = new HashSet<>();
    private Position princess;

    public Level(MapLevel entities) {
        this.width = entities.width();
        this.height = entities.height();

        for (int i = 0; i < width; i++)
            for (int j = 0; j < height; j++) {
                Position position = new Position(i, j);
                Entity entity = entities.get(i, j);
                Door d;
                switch (entity) {
                    case Stone:
                        elements.put(position, new Stone(position));
                        break;
                    case Tree:
                        elements.put(position, new Tree(position));
                        break;
                    case Box:
                        elements.put(position, new Box(position));
                        break;
                    case Key:
                        elements.put(position, new Key(position));
                        break;
                    case Heart:
                        elements.put(position, new Heart(position));
                        break;
                    case DoorPrevOpened:
                        d = new Door(position,true,false);
                        elements.put(position, d);
                        doorSet.add(d);
                        break;
                    case DoorNextOpened:
                        d = new Door(position,true,true);
                        elements.put(position, d);
                        doorSet.add(d);
                        break;
                    case DoorNextClosed:
                        d = new Door(position,false,true);
                        elements.put(position, d);
                        doorSet.add(d);
                        break;
                    case BombRangeInc:
                        elements.put(position, new BombRange(position, true));
                        break;
                    case BombRangeDec:
                        elements.put(position, new BombRange(position, false));
                        break;
                    case BombNumberInc:
                        elements.put(position, new BombCount(position,true));
                        break;
                    case BombNumberDec:
                        elements.put(position, new BombCount(position, false));
                        break;
                    case Monster:
                        characterMonster.add(position);
                        break;
                    case Princess:
                        princess = position;
                        break;
                    case Empty: break;
                    default:
                        throw new RuntimeException("EntityCode " + entity.name() + " not processed");
                }
            }
    }

    @Override
    public int width() {
        return this.width;
    }

    @Override
    public int height() {
        return this.height;
    }

    public Decor get(Position position) {
        if (inside(position)){return elements.get(position);}
        return null;
    }

    @Override
    public void remove(Position position) {
        elements.remove(position);
    }

    public Collection<Decor> values() {
        return elements.values();
    }


    @Override
    public boolean inside(Position position) {
        return position.y()<height && position.y()>=0 && position.x()<width && position.x()>=0;
    }

    @Override
    public void set(Position position, Decor decor) {
        if (!inside(position))
            throw new IllegalArgumentException("Illegal Position");
        if (decor != null)
            elements.put(position, decor);
    }

    @Override
    public Set<Door> doorSet() {
        return doorSet;
    }

    public HashSet<Position> monstersSet(){
        return characterMonster;
    }

    public Position getPrincess(){
        return princess;
    }
}
