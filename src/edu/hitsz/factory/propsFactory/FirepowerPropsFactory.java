package edu.hitsz.factory.propsFactory;

import edu.hitsz.props.AbstractProps;
import edu.hitsz.props.FirepowerProp;

public class FirepowerPropsFactory implements PropsFactory{
    public AbstractProps createProps(int locationX, int locationY, int speedX, int speedY){
        AbstractProps firepowerProp = new FirepowerProp(locationX, locationY, speedX, speedY);
        return firepowerProp;
    }
}
