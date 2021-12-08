# jaxws

JAX-WS 示例

JAX-WS RI是JAX WebService参考实现。相对于Axis2和CXF，JAX-WS RI是一个轻量级的框架。虽然是个轻量级框架，JAX-WS RI也提供了在Web服务器中发布Webservice的功能

执行单元测试 `WebServiceLiveTest.java`，测试自动启动服务端，测试完成后关闭服务端

## 运行项目

手工运行服务端：
* 运行 `UseMain.java` 的 main 方法，程序启动，在一段时间后自动关闭
* 在游览器中输入地址，如：http://localhost:9080/jaxws/services/welcome， http://localhost:9080/jaxws/services/student , 查看发布接口信息
* 运行测试用例 `WebServiceManualTest.java`

发布到Tomcat容器
* 需要下载 `Tomcat `，版本10以上（支持Servlet API 5.0）
* 打包项目，运行 `Maven` 命令：`mvn clean install` ，在 `target` 目录下，生成 `war` 包
* 部署war包到 `Tomcat` 容器，启动 `Tomcat`，成功后可在游览器中访问地址：http://localhost:9080/jaxws/services/welcome， http://localhost:9080/jaxws/services/student , 查看发布接口信息

## 参考

- [JAX-WS Users Guide](https://javaee.github.io/metro-jax-ws/doc/user-guide/ch03.html)
- [Introduction to JAX-WS](https://www.baeldung.com/jax-ws)
- [JAX-WS Samples](https://github.com/javaee/metro-jax-ws/tree/master/jaxws-ri/samples/src/main/samples)