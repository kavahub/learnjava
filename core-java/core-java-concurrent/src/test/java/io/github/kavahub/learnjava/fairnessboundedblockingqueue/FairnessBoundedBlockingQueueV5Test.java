package io.github.kavahub.learnjava.fairnessboundedblockingqueue;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * 
 * {@link FairnessBoundedBlockingQueueV5} 测试
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
@Disabled("V5 存在问题，测试不能通过")
public class FairnessBoundedBlockingQueueV5Test extends AbstractTestFairnessBoundedBlockingQueue {

    @Override
    protected Queue<Integer> newInstance(int size) {
        return new FairnessBoundedBlockingQueueV5<Integer>(size);
    }

    @Override
    @Test
    public void giveEmptyQueue_whenPoll_thenWaiting() throws InterruptedException {
        super.giveEmptyQueue_whenPoll_thenWaiting();
    }

    @Override
    @Test
    public void giveFullQueue_whenOffer_thenWaiting() throws InterruptedException {
        super.giveFullQueue_whenOffer_thenWaiting();
    }

    @Override
    @Test
    public void giveFullQueue_whenPoll_thenSuccess() {
        super.giveFullQueue_whenPoll_thenSuccess();
    }

    @Override
    @Test
    public void giveQueue_whenMutiThreadOffer_thenLimit() throws InterruptedException {
        super.giveQueue_whenMutiThreadOffer_thenLimit();
    }

    @Override
    @Test
    public void giveQueue_whenOfferAndPoll_thenSuccess() throws InterruptedException {
        super.giveQueue_whenOfferAndPoll_thenSuccess();
    }

    @Override
    @Test
    public void giveSize_whenOffer_thenSuccess() {
        super.giveSize_whenOffer_thenSuccess();
    }    

    
}
