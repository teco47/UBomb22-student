package fr.ubx.poo.ubomb.launcher;

import java.util.ArrayList;

public class MapLevelFactory {

    private final static char EOL = 'x';
    private int sizeX;
    private int sizeY;
    private MapLevel map;
    private final String level;

    public MapLevelFactory(String level) {
        this.level = level;
        createMap(level);
    }

    private void createMap(String level){
        sizeX = -1;
        sizeY = -1;

        ArrayList<Entity> entities = new ArrayList<>();
        for (int i = 0; i<level.length(); i++){
            if(level.charAt(i)==EOL){
                if(sizeX == -1){
                    sizeX = entities.size();
                }
                sizeY++;
                System.out.println("");
            } else {
                System.out.print(level.charAt(i));
                entities.add(Entity.fromCode(level.charAt(i)));
                if(Character.isDigit(level.charAt(i+1))){
                    for(int j = 1; j< Character.getNumericValue(level.charAt(i+1)); j++){
                        System.out.print(level.charAt(i));
                        entities.add(Entity.fromCode(level.charAt(i)));
                    }
                    i++;
                }
            }
        }

        if (sizeX != -1 && sizeY != -1){
            map = new MapLevel(sizeX,sizeY);
            for(int y=0; y < sizeY; y++){
                for(int x=0; x<sizeX; x++){
                    map.set(x,y,entities.get(x+(y*sizeX)));
                }
            }
        }
    }

    public MapLevel getMap(){return map;}
}
