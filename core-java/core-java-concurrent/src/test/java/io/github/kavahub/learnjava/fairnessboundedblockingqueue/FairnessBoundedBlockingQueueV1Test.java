package io.github.kavahub.learnjava.fairnessboundedblockingqueue;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class FairnessBoundedBlockingQueueV1Test extends AbstractTestFairnessBoundedBlockingQueue {

    @Override
    protected Queue<Integer> newInstance(int size) {
        return new FairnessBoundedBlockingQueueV1<Integer>(size);
    }

    @Override
    @Test
    @Disabled("不支持")
    public void giveEmptyQueue_whenPoll_thenWaiting() throws InterruptedException {
        super.giveEmptyQueue_whenPoll_thenWaiting();
    }

    @Override
    @Test
    @Disabled("不支持")
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
    @Disabled("不支持")
    public void giveQueue_whenMutiThreadOffer_thenLimit() throws InterruptedException {
        super.giveQueue_whenMutiThreadOffer_thenLimit();
    }

    @Override
    @Test
    @Disabled("不支持")
    public void giveQueue_whenOfferAndPoll_thenSuccess() throws InterruptedException {
        super.giveQueue_whenOfferAndPoll_thenSuccess();
    }

    @Override
    @Test
    public void giveSize_whenOffer_thenSuccess() {
        super.giveSize_whenOffer_thenSuccess();
    }


}
