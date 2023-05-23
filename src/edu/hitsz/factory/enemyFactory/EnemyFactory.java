package edu.hitsz.factory.enemyFactory;

import edu.hitsz.aircraft.AbstractAircraft;

public interface EnemyFactory {
    public abstract AbstractAircraft createEnemy(int speedX,int speedY,int hp);
}
