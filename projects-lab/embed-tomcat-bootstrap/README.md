# embed-tomcat-bootstrap

一般情况下，`Web` 项目需要手工部署到容器，这个项目使用内嵌 `Tomcat` 容器启动

## 运行项目

运行 `Maven` 命令打包项目：`mvn clean install` ，完成后就可以运行项目，如下：

```text
cd target
java -jar embed-tomcat-bootstrap-1.x.x-SNAPSHOT-jar-with-dependencies.jar
```

执行后，内嵌 Tomcat 启动，请不要关闭窗口。在游览器中访问地址：http://localhost:9080/employee

注意：发布时，`embed-tomcat-bootstrap-1.x.x-SNAPSHOT-jar-with-dependencies.jar` 和 `classes` 目录都需要

## 参考


