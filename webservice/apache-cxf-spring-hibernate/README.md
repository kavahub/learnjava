# apache-cxf-spring-hibernate

CXF JAX-RS Restful web service 集成 Spring 和 Hibernate 开发数据系统

## 运行项目

Maven 命令运行测试：`mvn clean install -Pintegration-test`

Maven 命令打包: `mvn clean install` 。成功后，`target/generated-sources/jaxb` 路径自动加入到 `classpath`，这是 `jaxb2-maven-plugin` 插件自动生成的代码。可以直接部署 `target` 目录下的 `war` 包，默认使用H2内存数据库

部署启动成功后，在游览器中安装 [`Boomerang - SOAP & REST Client`](https://microsoftedge.microsoft.com/addons/detail/boomerang-soap-rest-c/bhmdjpobkcdcompmlhiigoidknlgghfo?hl=zh-CN) 插件，导入文件 `apache-cxf-spring-hibernate_boomerang.json` 就可以手工调试了

## 参考文章

- [Apache CXF: JAX-RS Restful web service + Integrating with Spring & Hibernate](https://www.benchresources.net/apache-cxf-jax-rs-restful-web-service-integrating-with-spring-hibernate/#google_vignette)


