# apache-cxf-jaxrs

JAX-RS 示例

JAX-RS,全称为Java API for RESTful Web Services , 核心概念是resource，即面向资源。 

本项目基于 Apache CXF 框架，开发JAX-RS简单应用

## 运行项目

内嵌 `Tomcat` 容器集成测试，运行命令：`mvn clean install -Pintegration-test` 即可

部署方式：
* 手工运行服务端，运行 `RestfulServer.java` 的 main 方法，程序启动，在一段时间后自动关闭
* 使用 `Maven` 命令打包：`mvn clean install` ，部署 `target/apache-cxf-jaxrs.war` 到 `Tomcat` (9版本)

服务端运行成功后，在游览器中输入接口地址，如：http://localhost:9080/apache-cxf-jaxrs/courses/1 等等，也可以运行单元测试 ServiceIntegrationTest.java

## 参考


