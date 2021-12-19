package io.github.kavahub.learnjava;

import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.walkthrough.common.entity.Alert;
import org.apache.flink.walkthrough.common.entity.Transaction;
import org.apache.flink.walkthrough.common.sink.AlertSink;
import org.apache.flink.walkthrough.common.source.TransactionSource;

/**
 * 欺诈检测任务：定义数据流
 * 
 * <p>
 * 开发过程：
 * <ul>
 * <li>流执行环境，设置任务属性，创建源，执行任务</li>
 * <li>创建源，从外部系统获取数据，如：Apache Kafka, Rabbit MQ 等，获取的每个交易流包含：账户(accountId),
 * 交易时间(timestamp) , 交易额 (amount) 等信息</li>
 * </ul>
 * 
 * @author PinWei Wan
 * @since 1.0.2
 */
public class FraudDetectionJob {
    public static void main(String[] args) throws Exception {
        // 流执行环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // 创建源
        DataStream<Transaction> transactions = env
                .addSource(new TransactionSource())
                .name("transactions");

        // 定义处理
        DataStream<Alert> alerts = transactions
                // 通过id分组,相同id的流会被分配到同一个slot上
                .keyBy(Transaction::getAccountId)
                // 对每个分组进行欺诈校验
                .process(new FraudDetector())
                // 名称，方便调试
                .name("fraud-detector");

        // 定义输出
        alerts.addSink(new AlertSink())
                // 名称，方便调试
                .name("send-alerts");

        // 定义执行检测
        env.execute("Fraud Detection");
    }
}
