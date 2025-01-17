package com.mygdx.game.utility;

import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.Managers.MemoryManager;

import java.util.ArrayList;

import java.sql.Time;

public class GameSession {
    public GameState state;

    int balance;

    long nextEnemySpawnTime;
    long nextEnemySpawnTime2 = TimeUtils.millis() + GameSettings.ENEMY_SPAWN_TIME2;
    long nextEnemySpawnTime3 = TimeUtils.millis() + GameSettings.ENEMY_SPAWN_TIME3;
    int countReleasedEnemies;
    int countReleasedEnemies2;
    int countReleasedEnemies3;

    boolean levelFlag = true;
    private int level;
    long startRestTime;

    public GameSession() {

    }

    public void startGame() {
        state = GameState.PLAYING;
        balance = 1000;
        countReleasedEnemies = 0;
        countReleasedEnemies2 = 0;
        countReleasedEnemies3 = 0;
        level = 0;
        startRestTime = 0;
    }

    public void pauseGame() {
        state = GameState.PAUSED;
    }
    public void progressGame() {
        state = GameState.PROGRESS;
    }

    public void resumeGame() {
        state = GameState.PLAYING;
    }

    public void endGame() {
        state = GameState.ENDED;
        ArrayList<Integer> recordsTable = MemoryManager.loadRecordsTable();
        if (recordsTable == null) {
            recordsTable = new ArrayList<>();
        }
        int foundIdx = 0;
        for (; foundIdx < recordsTable.size(); foundIdx++) {
            if (recordsTable.get(foundIdx) < getLevel()) break;
        }
        recordsTable.add(foundIdx, getLevel());
        MemoryManager.saveTableOfRecords(recordsTable);
    }

    public boolean shouldSpawnEnemy() {
        if (countReleasedEnemies < GameSettings.ENEMY_COUNT) {
            if (nextEnemySpawnTime <= TimeUtils.millis()) {
                nextEnemySpawnTime = TimeUtils.millis() + GameSettings.ENEMY_SPAWN_TIME;
                countReleasedEnemies++;
                return true;
            }
        } else {
            System.out.println("OK");
            startRestTime = TimeUtils.millis();
        }
        return false;
    }

    public boolean shouldSpawnEnemy2() {
        if (countReleasedEnemies2 < GameSettings.ENEMY2_COUNT) {
            if (nextEnemySpawnTime2 <= TimeUtils.millis()) {
                nextEnemySpawnTime2 = TimeUtils.millis() + GameSettings.ENEMY_SPAWN_TIME2;
                countReleasedEnemies2++;
                return true;
            }
        }
        return false;
    }

    public boolean shouldSpawnEnemy3() {
        if (countReleasedEnemies3 < GameSettings.ENEMY3_COUNT) {
            if (nextEnemySpawnTime3 <= TimeUtils.millis()) {
                nextEnemySpawnTime3 = TimeUtils.millis() + GameSettings.ENEMY_SPAWN_TIME3;
                countReleasedEnemies3++;
                return true;
            }
        }
        return false;
    }

    public boolean isRest() {
        if (startRestTime + GameSettings.WAVE_REST_TIME > TimeUtils.millis()) {
            levelFlag = true;
            return true;
        } else {
            if (levelFlag) {
                countReleasedEnemies = 0;
                countReleasedEnemies2 = 0;
                countReleasedEnemies3 = 0;
                levelUp();
                levelFlag = false;
            }
            return false;
        }
    }

    public int getBalance() {
        return balance;
    }

    public void addBalance(int balance) {
        this.balance += balance;
    }

    public void reduceBalance(int balance) {
        this.balance -= balance;
    }

    public void levelUp() {
        level++;
        if (GameSettings.ENEMY_SPAWN_TIME > 600) {
            GameSettings.ENEMY_SPAWN_TIME -= 200;
        }
        GameSettings.ENEMY_SPEED += 0.3f;
        GameSettings.ENEMY_COUNT++;
        if (level % 2 == 0) {
            GameSettings.ENEMY2_SPEED += 0.1f;
            GameSettings.ENEMY2_COUNT++;
            GameSettings.ENEMY1_HEALTH++;
            GameSettings.ENEMY2_HEALTH++;
        }
        if (level % 5 == 0) {
            GameSettings.ENEMY3_HEALTH++;
            GameSettings.ENEMY3_SPEED += 0.1f;
        }
    }

    public int getLevel() {
        return level;
    }
}
