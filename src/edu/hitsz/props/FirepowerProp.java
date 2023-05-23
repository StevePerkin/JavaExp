package edu.hitsz.props;


import edu.hitsz.Strategy.Parallel;
import edu.hitsz.Strategy.Scatter;
import edu.hitsz.aircraft.HeroAircraft;
/**
 * 子弹道具
 */
public class FirepowerProp extends AbstractProps{
    public static int num = 0;
    public FirepowerProp (int locationX, int locationY, int speedX, int speedY){
        super(locationX, locationY, speedX, speedY);
    }
    @Override
    public void Effect(HeroAircraft heroAircraft) {
        Runnable r = ()->{
            heroAircraft.setShootStrategy(new Scatter(1));
            num +=1;
            try{
                Thread.sleep(5000);
            }catch (InterruptedException e){
                throw new RuntimeException(e);
            }
            num-=1;
            if (num==0)
                heroAircraft.setShootStrategy(new Parallel(1));
        };
        System.out.println("FireSupply active!");
        new Thread(r).start();
    }
}
