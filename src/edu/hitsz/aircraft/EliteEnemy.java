package edu.hitsz.aircraft;

import edu.hitsz.Strategy.Parallel;
import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;

import java.util.LinkedList;
import java.util.List;

public class EliteEnemy extends AbstractAircraft {
    private int shootNum = 1;
    private int power = 30;
    /**
     * 子弹射击方向 (向上发射：1，向下发射：-1)
     */
    private int direction = 1;
    /**
     * @param locationX 精英机位置x坐标
     * @param locationY 精英机位置y坐标
     * @param speedX 精英机射出的子弹的基准速度（精英机无特定速度）
     * @param speedY 精英机射出的子弹的基准速度（精英机无特定速度）
     * @param hp    初始生命值
     */
    public EliteEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
        shootStrategy = new Parallel(0);
    }

    @Override
    public void forward() {
        super.forward();
        // 判定 y 轴向下飞行出界
        if (locationY >= Main.WINDOW_HEIGHT ) {
            vanish();
        }
    }

    @Override
    public List<BaseBullet> shoot() {
        return shootStrategy.shoot(this.getLocationX(),this.getLocationY(),0,this.getSpeedY(),direction,power);
    }

}
