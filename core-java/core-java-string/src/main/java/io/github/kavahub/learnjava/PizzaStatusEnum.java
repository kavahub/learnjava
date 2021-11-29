package io.github.kavahub.learnjava;

/**
 * 
 * （辅助类）
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public enum PizzaStatusEnum {
    ORDERED(5) {
        @Override
        public boolean isOrdered() {
            return true;
        }
    },
    READY(2) {
        @Override
        public boolean isReady() {
            return true;
        }
    },
    DELIVERED(0) {
        @Override
        public boolean isDelivered() {
            return true;
        }
    };

    private int timeToDelivery;

    public boolean isOrdered() {
        return false;
    }

    public boolean isReady() {
        return false;
    }

    public boolean isDelivered() {
        return false;
    }

    public int getTimeToDelivery() {
        return timeToDelivery;
    }

    PizzaStatusEnum(int timeToDelivery) {
        this.timeToDelivery = timeToDelivery;
    }    
}
