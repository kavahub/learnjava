package io.github.kavahub.learnjava;

import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;

/**
 * Javassist 库实现
 * 
 * @author PinWei Wan
 * @since 1.0.1
 */
@Slf4j
public class TransformerWithJavassist  implements Transformer {
    private final static String TARGET_CLASS = "io/github/kavahub/learnjava/TargetClass";

    @Override
    public void transform(String args, Instrumentation inst) {
        inst.addTransformer(new ClassFileTransformer() {

            @Override
            public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                    ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
                
                // 仅处理TargetClass类
                if (className.equals(TARGET_CLASS)) {
                    log.info("transform class - {}" + className);

                    ElapseOfTimeClassWriter writer = new ElapseOfTimeClassWriter(className);
                    return writer.write();
                }

                return ClassFileTransformer.super.transform(loader, className, classBeingRedefined, protectionDomain,
                        classfileBuffer);
            }

        });
    }

    public static class ElapseOfTimeClassWriter {
        private final static String STOPWATCH_START = "\n final io.github.kavahub.learnjava.StopWatch.Clazz stopwatch = new io.github.kavahub.learnjava.StopWatch.Clazz(\"%s\");\n"
                + "\n stopwatch.start();\n";
        private final static String STOPWATCH_END = "\n stopwatch.end();\n";

        private final String className;

        public ElapseOfTimeClassWriter(String className) {
            this.className = className.replace("/", ".");
        }

        public byte[] write() {
            try {
                return wirte0();
            } catch (NotFoundException | CannotCompileException | IOException e) {
                throw new RuntimeException(e);
            }
        }

        private byte[] wirte0() throws NotFoundException, CannotCompileException, IOException {
            CtClass ctclass = ClassPool.getDefault().get(className);
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
                    bodyStr.append(String.format(STOPWATCH_START, methodName));
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
            log.info(" after update method list ...");
            for (CtMethod ctMethod : ctclass.getDeclaredMethods()) {
                log.info("Method name - {}", ctMethod.getName());
            }
            return ctclass.toBytecode();
        }
    }

}
