package io.github.kavahub.learnjava;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtNewMethod;
import lombok.extern.slf4j.Slf4j;

/**
 * Javassist 库实现，{@code StopWatch} 类实现计时功能
 * 
 * @author PinWei Wan
 * @since 1.0.1
 */
@Slf4j
public class ClassFileTransformerWithJavassistStopWatch implements ClassFileTransformer {
    final static String STOPWATCH_START = "\n io.github.kavahub.learnjava.StopWatch.start();\n";
    final static String STOPWATCH_END = "\n io.github.kavahub.learnjava.StopWatch.end();\n";

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
            ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        log.info(">>> transform class: {}", className);

        // 仅处理TargetClass类
        if (className.endsWith("/TargetClass")) {
            className = className.replace("/", ".");
            CtClass ctclass;
            try {
                // 使用全称,用于取得字节码类<使用javassist>
                ctclass = ClassPool.getDefault().get(className);
                for (CtMethod ctMethod : ctclass.getDeclaredMethods()) {
                    CtClass returnType = ctMethod.getReturnType();
                    // 无返回值方法
                    if (CtClass.voidType.equals(returnType)) {
                        String methodName = ctMethod.getName();
                        // 新定义一个方法
                        String newMethodName = "elapse$" + methodName;
                        // 将原来的方法名字修改
                        ctMethod.setName(newMethodName);
                        // 创建新的方法，复制原来的方法，名字为原来的名字
                        CtMethod newMethod = CtNewMethod.copy(ctMethod, methodName, ctclass, null);
                        // 构建新的方法体
                        StringBuilder bodyStr = new StringBuilder();
                        bodyStr.append("{");
                        bodyStr.append(STOPWATCH_START);
                        // 调用原有代码，类似于method();($$)表示所有的参数
                        bodyStr.append(newMethodName + "($$);\n");
                        bodyStr.append(STOPWATCH_END);
                        bodyStr.append("}");

                        // 替换新方法
                        newMethod.setBody(bodyStr.toString());
                        // 增加新方法
                        ctclass.addMethod(newMethod);
                    }
                }
                // 修改后的方法列表 会发现多了一个方法
                log.info(">>> after update method list ...");
                for (CtMethod ctMethod : ctclass.getDeclaredMethods()) {
                    log.info("Method name: ", ctMethod.getName());
                }
                return ctclass.toBytecode();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return ClassFileTransformer.super.transform(loader, className, classBeingRedefined, protectionDomain,
                classfileBuffer);
    }

}
