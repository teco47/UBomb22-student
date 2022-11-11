package fr.ubx.poo.ubomb.game;

public class BombParameter {
    private int count, range;

    public BombParameter(int count){
        this.count=count;
        range = 1;
    }

    public int getCount() {
        return count;
    }

    public int getRange() {
        return range;
    }

    public void updateCount(int count) {
        this.count += count;
    }

    public void updateRange(int range) {
        this.range += range;
    }
}
