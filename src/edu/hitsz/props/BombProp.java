package edu.hitsz.props;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.observer.MyObserver;
import thread.MusicThread;

import java.util.LinkedList;
import java.util.List;

public class BombProp extends AbstractProps{
    private List<MyObserver> observers = new LinkedList<>();
    public void setEnemyAircrafts(List<MyObserver> observers){
        this.observers = observers;
    }
    public void deleteEnemyAircrafts(){
        this.observers=null;
    }

    public void notifyAllObservers(){
        for(MyObserver observer:observers){
            observer.update();
        }
    }
    public BombProp (int locationX, int locationY, int speedX, int speedY){
        super(locationX, locationY, speedX, speedY);
    }
    @Override
    public void Effect(HeroAircraft heroAircraft) {
        notifyAllObservers();
        System.out.println("BombSupply active!");
        new MusicThread("src/videos/bomb_explosion.wav").start();
    }
}
