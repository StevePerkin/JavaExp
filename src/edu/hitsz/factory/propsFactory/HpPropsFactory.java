package edu.hitsz.factory.propsFactory;

import edu.hitsz.props.AbstractProps;
import edu.hitsz.props.HpProp;

public class HpPropsFactory implements PropsFactory{
    public AbstractProps createProps(int locationX, int locationY, int speedX, int speedY){
        AbstractProps hpProps = new HpProp(locationX, locationY, speedX, speedY);
        return hpProps;
    }
}
