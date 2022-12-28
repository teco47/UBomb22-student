package fr.ubx.poo.ubomb.game;

public class BombParameter {
    private int maxCount, currentCount, range;

    public BombParameter(int count){
        this.maxCount = this.currentCount = count;
        range = 1;
    }
    public int getCurrentCount() {
        return currentCount;
    }

    public int getMaxCount() {
        return maxCount;
    }

    public int getRange() {
        return range;
    }

    public void updateMaxCount(int count) {
        if(maxCount+count>=0) {
            this.maxCount += count;
            if (!(this.currentCount == 0 && count < 0)) {
                this.currentCount += count;
            }
        }
    }
    public boolean retrieveBomb(int count) {
        if(currentCount+count < 0){
            return false;
        }
        currentCount+=count;
        if (currentCount > maxCount){
            currentCount = maxCount;
        }
        return true;
    }


    public void updateRange(int range) {
        this.range += range;
        if(this.range < 0){ this.range = 0; }
    }
}
