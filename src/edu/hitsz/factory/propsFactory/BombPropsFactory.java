package edu.hitsz.factory.propsFactory;

import edu.hitsz.props.AbstractProps;
import edu.hitsz.props.BombProp;

public class BombPropsFactory implements  PropsFactory{
    public AbstractProps createProps(int locationX, int locationY, int speedX, int speedY){
        AbstractProps bombProps = new BombProp(locationX, locationY, speedX, speedY);
        return bombProps;
    }
}
