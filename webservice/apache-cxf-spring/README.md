# apache-cxf-spring

Apache CXF 与 Spring 集成开发

## 运行项目

使用 `Maven` 命令运行测试：`mvn clean install -Pintegration-test`

运行服务器端，如下方式：
* 运行 `ServicesServer.java` 的 main 方法，程序启动，在一段时间后自动关闭
* 使用 `Maven` 命令打包：`mvn clean install` ，部署 `target/apache-cxf-spring.war` 到 `Tomcat` (9版本)

服务端运行成功后，在游览器中输入接口地址，如：http://localhost:9080/apache-cxf-spring/services/student?wsdl 等等；运行单元测试 StudentIntegrationTest.java

## 参考


