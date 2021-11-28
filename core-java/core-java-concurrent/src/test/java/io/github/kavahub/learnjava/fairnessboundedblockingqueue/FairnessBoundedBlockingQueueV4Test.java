package io.github.kavahub.learnjava.fairnessboundedblockingqueue;

import org.junit.jupiter.api.Test;

/**
 * 
 * {@link FairnessBoundedBlockingQueueV4} 测试
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
public class FairnessBoundedBlockingQueueV4Test extends AbstractTestFairnessBoundedBlockingQueue {

    @Override
    protected Queue<Integer> newInstance(int size) {
        return new FairnessBoundedBlockingQueueV4<Integer>(size);
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
