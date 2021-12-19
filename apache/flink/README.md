# flink

`Apache Flink` 是一个开源流处理框架，用于分布式流和批处理数据处理的开源平台。 `Flink` 是具有多个API的流数据流引擎，用于创建面向数据流的应用程序，支持流处理和批处理


`Apache Flink` 关键功能:
* 处理引擎，支持实时 `Streaming` 和批处理 `Batch`
* 支持各种窗口范例
* 支持有状态流
* `Faul Tolerant`和高吞吐量
* 复杂事件处理（CEP）
* 背压处理
* 与现有 `Hadoop` 堆栈轻松集成
* 用于进行机器学习和图形处理的库。

`Apache Flink` 核心API功能：
* 每个Flink程序都对分布式数据集合执行转换。 提供了用于转换数据的各种功能，包括过滤，映射，加入，分组和聚合。
* `Flink` 中的接收器操作用于接受触发流的执行以产生所需的程序结果 ，例如将结果保存到文件系统或将其打印到标准输出
* `Flink` 转换是惰性的，这意味着它们在调用接收器操作之前不会执行
* `Flink API` 支持两种操作模式 - 批量操作和实时操作。 如果正在处理可以批处理模式处理的有限数据源，则将使用 DataSet  API。如果您想要实时处理无限数据流，您需要使用 DataStream  API

## 参考

- [Try Flink](https://nightlies.apache.org/flink/flink-docs-release-1.14/docs/try-flink/)



