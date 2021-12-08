# jaxws-tomcat-auth

JAX-WS 示例

JAX-WS 使用 `Tomcat` 自带的安全认证

## 运行项目

发布到Tomcat容器
* 需要下载 `Tomcat` ，版本10以上（支持 `Servlet API 5.0`）
* 项目打包，使用 `maven` 命令：`mvn clean install` , 在 `target` 目录下，会存在 `war` 包
* 配置 `Tomcat` 使用认证，添加 `operator` 角色，添加一个用户拥有该角色。如果使用IDE的插件，配置应该在插件中完成
* 启动 `Tomcat`， 可以运行 `HelloWSManualTest.java` 测试

## 参考

