package fr.ubx.poo.ubomb.game;

public record Configuration(Position playerPosition, int bombBagCapacity, long bombStepTimer, int playerLives, long playerInvisibilityTime,
                            int monsterVelocity, long monsterInvisibilityTime) {
}
