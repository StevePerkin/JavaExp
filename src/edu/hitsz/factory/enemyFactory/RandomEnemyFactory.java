package edu.hitsz.factory.enemyFactory;

import edu.hitsz.aircraft.AbstractAircraft;

import java.util.Random;
public class RandomEnemyFactory {
    private int mode;
    public RandomEnemyFactory(int mode){
        this.mode=mode;
    }
    public AbstractAircraft creatEnemyAircraft() {
        Random random = new Random();
        EnemyFactory enemyFactory;
        if (random.nextInt(100) % (mode + 1) == 0) {
            enemyFactory = new MobEnemyFactory();
        } else {
            enemyFactory = new EliteEnemyFactory();
        }
        AbstractAircraft enemyAircraft=enemyFactory.createEnemy(0,6+mode*1,30*mode);
        return enemyAircraft;
    }
    public AbstractAircraft creatBossEnemy(int num){
        AbstractAircraft bossEnemy;
        EnemyFactory enemyFactory=new BossEnemyFactory();
        if(mode==2){
            bossEnemy=enemyFactory.createEnemy(3,0,300);
        }
        else{
            bossEnemy=enemyFactory.createEnemy(3,0,300+120*num);
        }
        return bossEnemy;
    }
}
