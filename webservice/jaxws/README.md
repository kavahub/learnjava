# jaxws

JAX-WS RI是JAX WebService参考实现。相对于Axis2和CXF，JAX-WS RI是一个轻量级的框架。虽然是个轻量级框架，JAX-WS RI也提供了在Web服务器中发布Webservice的功能

项目做了如下工作：
* 创建了两个 WebService 服务， 其中的一个服务使用 SOAPHandler 添加安全认证功能，客户端需要在请求头添加用户和密码信息；
* 实现了两个中方式发布服务：使用命令行（main）方法和 tomcat 容器
* 内嵌tomcat服务集成测试
* 内嵌命令行服务集成测试

## 参考文章

- [JAX-WS Users Guide](https://javaee.github.io/metro-jax-ws/doc/user-guide/ch03.html)
- [Introduction to JAX-WS](https://www.baeldung.com/jax-ws)
- [JAX-WS Samples](https://github.com/javaee/metro-jax-ws/tree/master/jaxws-ri/samples/src/main/samples)