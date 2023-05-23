package edu.hitsz.factory.propsFactory;

import edu.hitsz.props.AbstractProps;

public interface PropsFactory {
    public abstract AbstractProps createProps(int locationX, int locationY, int speedX, int speedY);

}
