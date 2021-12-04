# jaxws-tomcat-auth

JAX-WS 使用 Tomcat 自带的安全认证

## 运行项目

发布到Tomcat容器
* 需要下载 Tomcat ，版本10以上（支持Servlet API 5.0）
* 项目打包，使用maven命令：mvn clean install , 在target目录下，会存在war包
* 配置 Tomcat 使用认证。如果使用IDE的插件，配置应该在插件中完成
* 启动 Tomcat， 可以运行 HelloWSManualTest 测试

## 参考文章

