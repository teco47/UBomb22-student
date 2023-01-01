package fr.ubx.poo.ubomb.game;

public class BombParameter {
    private int toReduce, maxCount, currentCount, range;

    public BombParameter(int count){
        this.toReduce = 0;
        this.maxCount = this.currentCount = count;
        range = 1;
    }
    public int getCurrentCount() {
        return currentCount;
    }

    public int getRange() {
        return range;
    }

    public void updateMaxCount(int count) {

        if(count>0) {
            updateCurrentCount(count);
            maxCount+=count;
        }else{
            if (maxCount+count>0){ // sum 1+
                maxCount+=count;
                currentCount+=count;
                if (currentCount < 0){
                    toReduce+=currentCount;
                    currentCount=0;
                }
            }
        }
    }
    public boolean updateCurrentCount(int count) {
        if (count<0){
            if(currentCount+count < 0){// sum <0
                return false;
            }else{
                currentCount--;
            }
        }else{ // count positif
            toReduce+=count;
            if(toReduce>0){
                currentCount += toReduce;
                toReduce = 0;
            }
        }

        return true;
    }


    public void updateRange(int range) {
        this.range += range;
        if(this.range < 0){ this.range = 0; }
    }
}
