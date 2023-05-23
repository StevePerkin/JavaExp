package edu.hitsz.Strategy;

import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.bullet.HeroBullet;

import java.util.LinkedList;
import java.util.List;

/**
 * 子弹直射策略
 */

public class Parallel implements ShootStrategy {
    private int shootNum = 1;
    private int type;
    public Parallel(int type){
        this.type = type;
    }
    @Override
    public List<BaseBullet> shoot(int positionX,int positionY,int getSpeedX,int getSpeedY,int direction,int power){
        List<BaseBullet> res = new LinkedList<>();
        int x = positionX;
        int y = positionY + direction*2;
        int speedX = 0;
        int speedY = getSpeedY + direction*5;
        BaseBullet baseBullet;
        for (int i = 0; i<shootNum; i++){
            if(type==1) {
                baseBullet = new HeroBullet(x + (i * 2 - shootNum + 1) * 10, y, speedX, speedY, power);
            }
            else {
                baseBullet = new EnemyBullet(x + (i * 2 - shootNum + 1) * 10, y, speedX, speedY, power);
            }
            res.add(baseBullet);
        }
        return res;
    }

}
