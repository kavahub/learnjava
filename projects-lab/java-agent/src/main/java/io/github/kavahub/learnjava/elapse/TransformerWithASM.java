package io.github.kavahub.learnjava.elapse;

import static org.objectweb.asm.Opcodes.ASM7;
import static org.objectweb.asm.Opcodes.INVOKESTATIC;
import static org.objectweb.asm.Opcodes.IRETURN;
import static org.objectweb.asm.Opcodes.RETURN;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * TODO
 * 
 * @author PinWei Wan
 * @since 1.0.1
 */
public class TransformerWithASM implements ClassFileTransformer {

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
            ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {

        System.out.println(">>> transform class: " + className);

        // 仅处理Main结尾的类
        if (className.endsWith("Main")) {
            ElapseClassWriter writer = new ElapseClassWriter(classfileBuffer);
            return writer.perform();
        }

        return ClassFileTransformer.super.transform(loader, className, classBeingRedefined, protectionDomain,
                classfileBuffer);
    }

    public static class ElapseClassWriter {
        private final ClassReader reader;
        private final ClassWriter writer;

        public ElapseClassWriter(byte[] contents) {
            reader = new ClassReader(contents);
            writer = new ClassWriter(reader, 0);
        }

        public byte[] perform() {
            ElapseClassAdapter elapseClassAdapter = new ElapseClassAdapter(writer);
            reader.accept(elapseClassAdapter, 0);
            return writer.toByteArray();
        }
    }

    public static class ElapseClassAdapter extends ClassVisitor {
        public ElapseClassAdapter(ClassVisitor classVisitor) {
            super(ASM7, classVisitor);
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String descriptor, String signature,
                String[] exceptions) {
            MethodVisitor methodVisitor = super.visitMethod(access, name, descriptor, signature, exceptions);

            return new ElapseMethodAdapter(methodVisitor);

        }

    }

    public static class ElapseMethodAdapter extends MethodVisitor {
        private final static String owner = "io/github/kavahub/learnjava/elapse/TransformerWithASM$StopWath";

        public ElapseMethodAdapter(MethodVisitor methodVisitor) {
            super(ASM7, methodVisitor);
        }

        /**
         * 方法开始被访问时调用
         */
        @Override
        public void visitCode() {
            // 方法开始时，插入start代码
            visitMethodInsn(INVOKESTATIC, owner, "start", "()V", false);
            super.visitCode();
        }

        @Override
        public void visitInsn(int opcode) {
            if ((opcode >= IRETURN && opcode <= RETURN)) {
                // 方法返回时, 插入end代码
                visitMethodInsn(Opcodes.INVOKESTATIC, owner, "end", "()V", false);
            }
            mv.visitInsn(opcode);
        }

    }

    /**
     * 
     * 秒表，计算开始到结束直接的毫秒数
     *
     * @author PinWei Wan
     * @since 1.0.1
     */
    public static class StopWath {
        static ThreadLocal<Long> t = new ThreadLocal<Long>();

        public static void start() {
            t.set(System.currentTimeMillis());
        }

        public static void end() {
            System.out.println(
                    Thread.currentThread().getStackTrace()[2] + " elapse of time:" + (System.currentTimeMillis() - t.get()));

            t.remove();          
        }
    }
}
