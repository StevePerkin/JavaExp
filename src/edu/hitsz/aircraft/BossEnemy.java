package edu.hitsz.aircraft;

import edu.hitsz.Strategy.Scatter;
import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;

import java.util.LinkedList;
import java.util.List;

/**
 * boss敌机生成
 * @author ChenYifan
 */

public class BossEnemy extends AbstractAircraft{
    /**攻击方式 */

    /**
     * 子弹一次发射数量
     */
    private int shootNum = 3;

    /**
     * 子弹伤害
     */
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
    public BossEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
        shootStrategy = new Scatter(0);
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
    /**
     * 通过射击产生子弹
     * @return 射击出的子弹List
     */
    public List<BaseBullet> shoot() {
        return shootStrategy.shoot(this.getLocationX(),this.getLocationY(),this.speedX,this.getSpeedY(),direction,power);
    }
    public void update(){
        decreaseHp(this.maxHp/2);
    }

}
