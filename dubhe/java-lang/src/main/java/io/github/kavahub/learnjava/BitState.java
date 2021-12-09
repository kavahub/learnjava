package io.github.kavahub.learnjava;

import java.io.Serializable;

/**
 * 
 * 状态位
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class BitState implements Serializable {

    private volatile int state;

    public BitState(int state) {
        this.state = state;
    }

    protected static int bit(int num) {
        return 1 << num;
    }

    public int get() {
        return state;
    }

    public void set(int state) {
        this.state = state;
    }

    public boolean is(int state) {
        return this.state == state;
    }

    public boolean not(int state) {
        return this.state != state;
    }

    public boolean isAnyOf(int mask) {
        return (this.state & mask) != 0;
    }

    public boolean isNotOf(int mask) {
        return (this.state & mask) == 0;
    }

    public boolean equalTo(BitState that) {
        return this.state == that.state;
    }
    
}
