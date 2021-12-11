package io.github.kavahub.learnjava;

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

/**
 * ASM 库实现
 * 
 * @author PinWei Wan
 * @since 1.0.1
 */
public class ClassFileTransformerWithASM implements ClassFileTransformer {

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
            ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {

        System.out.println(">>> transform class: " + className);

        // 仅处理Main结尾的类
        if (className.endsWith("/TargetClass")) {
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
        private final static String owner = "io/github/kavahub/learnjava/StopWatch";

        public ElapseMethodAdapter(MethodVisitor methodVisitor) {
            super(ASM7, methodVisitor);
        }

        /**
         * 方法开始被访问时调用
         */
        @Override
        public void visitCode() {
            // 方法开始时，插入StopWatch代码，调用start方法
            mv.visitMethodInsn(INVOKESTATIC, owner, "start", "()V", false);
            super.visitCode();
        }

        @Override
        public void visitInsn(int opcode) {
            if ((opcode >= IRETURN && opcode <= RETURN)) {
                // 方法返回时, 插入StopWatch代码，调用end方法
                visitMethodInsn(INVOKESTATIC, owner, "end", "()V", false);
            }
            mv.visitInsn(opcode);
        }

    }
}
