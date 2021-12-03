# jaxws-tomcat-auth

JAX-WS 使用 Tomcat 自带的安全认证

操作步骤如下：
* 项目打包，使用maven命令：mvn clean install , 在target目录下，会存在war包
* 配置tomcat server (10以上版本) 使用认证。如果使用IDE的插件，配置应该在插件中完成
* 启动tomcat server， 可以运行 HelloWSManualTest 测试

## 参考文章

