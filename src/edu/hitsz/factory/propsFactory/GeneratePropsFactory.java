package edu.hitsz.factory.propsFactory;

import edu.hitsz.props.AbstractProps;

import java.util.Random;

public class GeneratePropsFactory implements PropsFactory{
    public AbstractProps createProps(int locationX, int locationY, int speedX, int speedY){
        var random=new Random();
        int random_num= random.nextInt(99);
        AbstractProps props;
        PropsFactory propsFactory;
        if(random_num%3==0){
            propsFactory=new HpPropsFactory();
        }
        else if (random_num%3==1) {
            propsFactory=new BombPropsFactory();
        }
        else {
            propsFactory=new FirepowerPropsFactory();
        }
        props=propsFactory.createProps(locationX,locationY,speedX,speedY);
        return  props;
    }
}
