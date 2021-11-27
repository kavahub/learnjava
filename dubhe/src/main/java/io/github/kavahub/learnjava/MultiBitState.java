package io.github.kavahub.learnjava;

/**
 * 
 * 多重状态位
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class MultiBitState extends BitState {

    public MultiBitState(int state) {
        super(state);
    }

    public void include(int mask) {
        set(get() | mask);
    }

    public void exclude(int mask) {
        set(get() & ~mask);
    }
    
}
