# FAQ

Q: Maven 命令只编译打包父项目

```text
mvn --non-recursive clean install
```

Q: Maven 命令编译打包项目， 不运行测试

```text
mvn clean install -Dmaven.test.skip=true
```
