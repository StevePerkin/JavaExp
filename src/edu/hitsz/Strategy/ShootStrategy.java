package edu.hitsz.Strategy;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.bullet.BaseBullet;

import java.util.List;

public interface ShootStrategy {
    /**
     * 创建子弹角色 子弹射出的策略
     */
    List<BaseBullet> shoot(int positionX,int positionY,int getSpeedX,int getSpeedY,int direction,int power);
}
