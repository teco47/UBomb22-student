/*
 * Copyright (c) 2020. Laurent Réveillère
 */

package fr.ubx.poo.ubomb.engine;

import fr.ubx.poo.ubomb.game.Game;
import fr.ubx.poo.ubomb.go.GameObject;
import fr.ubx.poo.ubomb.go.character.Character;

import java.io.Serializable;

public class Timer {
    private final long duration;
    private long startTime;
    private boolean running = false;
    private boolean requested = false;
    private long remaining;

    private final GameObject go;
    private final String flag;

    // Set a timer for a duration in ms
    public Timer(long duration, GameObject go, String name) {
        this.duration = duration;
        remaining = duration;
        this.go = go;
        flag = name;
    }

    public void update(long now) {
        // time is in ns
        if (running) {
            remaining = duration - (now - startTime) / 1000000;
            if (remaining < 0) {
                running = false;
            }
        } else if (requested) {
            running = true;
            requested = false;
            startTime = now;
            remaining = duration;
        }
    }

    public long remaining() {
        return remaining;
    }

    public void start() {
        if (!running)
            requested = true;
    }

    public boolean isRunning() {
        return running || requested;
    }

    public void trigger(){
        go.trigger(flag);
    }
}
