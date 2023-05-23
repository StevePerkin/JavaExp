package edu.hitsz.props;

import edu.hitsz.aircraft.HeroAircraft;

public class HpProp extends AbstractProps{
    public HpProp(int locationX, int locationY, int speedX, int speedY){
        super(locationX, locationY, speedX, speedY);
    }
    public int increase = 100;
    @Override
    public void Effect(HeroAircraft heroAircraft) {
        heroAircraft.increaseHp(increase);
        System.out.println("HpSupply active!");
    }

}
