# learnjava

[![Gitee stars](https://gitee.com/pinweiwan/learnjava/badge/star.svg)](https://gitee.com/pinweiwan/learnjava/stargazers)
[![Gitee forks](https://gitee.com/pinweiwan/learnjava/badge/fork.svg)](https://gitee.com/pinweiwan/learnjava/members)
[![License](https://img.shields.io/github/license/kavahub/learnjava.svg)](https://github.com/kavahub/learnjava/blob/main/LICENSE)
[![GitHub stars](https://img.shields.io/github/stars/kavahub/learnjava?style=flat-square&logo=GitHub)](https://github.com/kavahub/learnjava/stargazers)
[![GitHub forks](https://img.shields.io/github/forks/kavahub/learnjava?style=flat-square&logo=GitHub)](https://github.com/kavahub/learnjava/network/members)
[![GitHub watchers](https://img.shields.io/github/watchers/kavahub/learnjava?style=flat-square&logo=GitHub)](https://github.com/kavahub/learnjava/watchers)
[![GitHub release](https://img.shields.io/github/release/kavahub/learnjava?style=flat-square&logo=GitHub?color=blu)](https://github.com/kavahub/learnjava/releases)



[`Gitee`](https://gitee.com/pinweiwan/learnjava)

[`GitHub`](https://github.com/kavahub/learnjava)

#### 介绍

Java 编程语言学习，内容包含多个方面，如：字符串，日期，集合，IO, NIO, 网络，多线程等等，其中还包含第三方库，如：Apache Commons, Guava等等。

学习编程语言从测试开始。项目包含大量的示例，测试代码及代码注释。在 `README.md`中，还有在网络中收集的技术文章

如果有好的代码，文章推荐，或者疑问等等，请在 [`issues`](https://gitee.com/pinweiwan/learnjava/issues) 中给我留言

#### 特色

项目说明：
* 项目导入到IDE中，示例及测试用例可以直接运行
* 关注单个类的应用，适合初级开发者
* 示例及测试用例代码短小精悍，通俗易懂
* 代码中包含注释，代码与注释相互辅助，有血有肉
* 单元测试，集成测试，性能测试都有，适合高级开发者
* 工作中的参考书，有疑问，想法，写个测试验证一下

几种测试说明：
* `*Test.java` 单元测试，单个类的测试，有断言
* `*ManualTest.java` 手工测试，单个或多个类的测试，没有断言，通常用控制台输出查看结果
* `*LiveTest.java` 集成测试，测试依赖某些服务，在测试开始前，会自动启动服务，完成后关闭
* `*IntegrationTest.java` 集成测试，测试依赖某些服务，但不会自动启动服务，而是依靠工具启动， 如：`cargo-maven2-plugin`

#### 运行项目

克隆代码到本地，运行 Maven 命令编译打包项目，也包括运行测试：

```text
mvn clean install
```

如果需要运行集成测试（依赖某些服务，可能会失败），运行 `Maven`命令：

```text
mvn clean install -Pintegration-test
```

#### 其他项目

- [`eugenp`](https://github.com/eugenp/tutorials) : This project is a collection of small and focused tutorials 
