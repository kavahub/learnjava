package io.github.kavahub.learnjava;

import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.KeyedProcessFunction;
import org.apache.flink.util.Collector;
import org.apache.flink.walkthrough.common.entity.Alert;
import org.apache.flink.walkthrough.common.entity.Transaction;

/**
 * 欺诈检测者，业务逻辑定义：检测欺诈交易
 * 
 * @author PinWei Wan
 * @since 1.0.2
 */
public class FraudDetector extends KeyedProcessFunction<Long, Transaction, Alert> {

    private static final double SMALL_AMOUNT = 1.00;
    private static final double LARGE_AMOUNT = 500.00;
    private static final long ONE_MINUTE = 60 * 1000;

    // 标志：交易额小于1，设置true；否则设置false或者null。通常与 keyBy 一起使用
    private transient ValueState<Boolean> flagState;
    // 上次交易时间
    private transient ValueState<Long> timerState;

    /**
     * 使用open完成使用前的注册,相当于初始化一个Boolean的状态
     */
    @Override
    public void open(Configuration parameters) {
        ValueStateDescriptor<Boolean> flagDescriptor = new ValueStateDescriptor<>(
                "flag",
                Types.BOOLEAN);
        flagState = getRuntimeContext().getState(flagDescriptor);

        ValueStateDescriptor<Long> timerDescriptor = new ValueStateDescriptor<>(
                "timer-state",
                Types.LONG);
        timerState = getRuntimeContext().getState(timerDescriptor);
    }

    @Override
    public void onTimer(long timestamp, OnTimerContext ctx, Collector<Alert> out) {
        // 当定时器被触发时，重置状态。
        timerState.clear();
        flagState.clear();
    }

    @Override
    public void processElement(
            Transaction transaction,
            Context context,
            Collector<Alert> collector) throws Exception {

        // 获取当前标志
        Boolean lastTransactionWasSmall = flagState.value();

        // 检测标志
        if (lastTransactionWasSmall != null) {
            if (transaction.getAmount() > LARGE_AMOUNT) {
                // 检测到欺诈
                Alert alert = new Alert();
                alert.setId(transaction.getAccountId());

                collector.collect(alert);
            }

            // 复位
            cleanUp(context);
        }

        if (transaction.getAmount() < SMALL_AMOUNT) {
            // 欺诈前提条件满足

            // 设置标志true
            flagState.update(true);

            // 设置一个在当前时间一分钟后触发的定时器
            long timer = context.timerService().currentProcessingTime() + ONE_MINUTE;
            context.timerService().registerProcessingTimeTimer(timer);
            timerState.update(timer);
        }
    }

    private void cleanUp(Context ctx) throws Exception {
        // 删除时间触发器
        Long timer = timerState.value();
        ctx.timerService().deleteProcessingTimeTimer(timer);
    
        // clean up all state
        timerState.clear();
        flagState.clear();
    }
}
