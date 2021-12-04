# sse-jaxrs

Server-Sent Events（以下简称 SSE）, 服务器向浏览器推送信息。SSE 是单向通道，只能服务器向浏览器发送

SSE 优点：
* 使用 HTTP 协议，现有的服务器软件都支持。
* 轻量级，使用简单
* 默认支持断线重连
* 一般只用来传送文本，二进制数据需要编码后传送
* 支持自定义发送的消息类型

## 运行项目

首先，运行 Maven 命令打包项目： mvn clean install ，如下方式启动服务：
* 命令行启动服务， 打包完成后，运行命令： target/liberty/bin/server.bat run
* Maven启动服务，运行 Maven 命令：mvn liberty:run

服务启动后，打开游览器可以访问地址：http://localhost:9080/sse-jaxrs/sse.html ， http://localhost:9080/sse-jaxrs/sse-broadcast.html

## FAQ

```text
Error occurred during initialization of boot layer
java.lang.module.FindException: Module java.instrument not found
```

缺少模块。Java 已经模块化了，使用 jmod 工具定制jre


## 参考文章

- [Server-Sent Events (SSE) In JAX-RS](https://www.baeldung.com/java-ee-jax-rs-sse)
- [ci.maven](https://github.com/OpenLiberty/ci.maven) Collection of Maven plugins and archetypes for managing Open Liberty and WebSphere Liberty servers and applications.
- [IBM 开源动态的应用服务器运行时环境 Open Liberty](https://www.oschina.net/news/88879/open-sourcing-liberty)