package edu.hitsz.props;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.basic.AbstractFlyingObject;
import edu.hitsz.application.Main;
/**
 * 所有种类道具的父类
 * 道具（Firepower, Bomb, Hp）
 *
 *
 * @author Opus
 */


public abstract class AbstractProps extends AbstractFlyingObject {

    public AbstractProps(int locationX, int locationY, int speedX, int speedY){
        super(locationX,locationY,speedX,speedY);

    }


    @Override
    public void forward() {
        super.forward();

        // 判定 x 轴出界
        if (locationX <= 0 || locationX >= Main.WINDOW_WIDTH) {
            vanish();
        }

        // 判定 y 轴出界
        if (speedY > 0 && locationY >= Main.WINDOW_HEIGHT ) {
            // 向下飞行出界
            vanish();
        }else if (locationY <= 0){
            // 向上飞行出界
            vanish();
        }
    }

    public abstract void Effect(HeroAircraft heroAircraft);

}



