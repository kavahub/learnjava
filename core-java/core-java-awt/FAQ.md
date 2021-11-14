Q:
如何找到AWT包
A:
JDK11默认没有jre，可以使用命令安装。
1. 使用管理员打开CMD
2. 进入到JAVA_HOME目录，执行命令：
> bin/jlink.exe --module-path jmods --add-modules java.desktop --output /jre
完成后就会生产jre目录了
3. 配置环境变量classpath=.;%JAVA_HOME%\lib;%JAVA_HOME%\jre\lib