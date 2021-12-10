package io.github.kavahub.learnjava;

import static org.objectweb.asm.Opcodes.ACC_PUBLIC;
import static org.objectweb.asm.Opcodes.ACC_STATIC;
import static org.objectweb.asm.Opcodes.ASM7;
import static org.objectweb.asm.Opcodes.V1_5;

import java.io.IOException;
import java.io.PrintWriter;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;
import org.objectweb.asm.util.TraceClassVisitor;

import lombok.extern.slf4j.Slf4j;

/**
 * 使用 ASM 库，给 {@code java.lang.Integer} 类增强功能：添加共有属性，修改私有方法属性，添加接口
 * 
 * <p>
 * ASM API 提供两种风格与 Java class 交互：event-based 和 tree-based ，本类使用了第一种
 * 
 * <p>
 * {@link ClassVisitor} 定义在读取Class字节码时会触发的事件，如类头解析完成、注解解析、
 * 字段解析、方法解析等
 * 
 * @author PinWei Wan
 * @since 1.0.1
 */

@Slf4j
public class IntegerClassWriter {
    // 读取并解析Class字节码
    ClassReader reader;
    // 它实现了ClassVisitor接口，用于拼接字节码
    ClassWriter writer;
    AddFieldAdapter addFieldAdapter;
    AddInterfaceAdapter addInterfaceAdapter;
    PublicizeMethodAdapter pubMethAdapter;
    final static String CLASSNAME = "java.lang.Integer";
    final static String CLONEABLE = "java/lang/Cloneable";

    public IntegerClassWriter() {

        try {
            reader = new ClassReader(CLASSNAME);
            writer = new ClassWriter(reader, 0);

        } catch (IOException ex) {
            log.error("{}", ex);
        }
    }

    public IntegerClassWriter(byte[] contents) {
        reader = new ClassReader(contents);
        writer = new ClassWriter(reader, 0);
    }

    public static void main(String[] args) {
        IntegerClassWriter ccw = new IntegerClassWriter();
        ccw.publicizeMethod();
    }

    /**
     * 添加共有属性：{@code aNewBooleanField}
     * 
     * @return
     */
    public byte[] addField() {
        addFieldAdapter = new AddFieldAdapter("aNewBooleanField", org.objectweb.asm.Opcodes.ACC_PUBLIC, writer);
        reader.accept(addFieldAdapter, 0);
        return writer.toByteArray();
    }

    /**
     * 修改私有方法属性: {@code toUnsignedString0} 是 Integer 类的私有方法
     * 
     * @return
     */
    public byte[] publicizeMethod() {
        pubMethAdapter = new PublicizeMethodAdapter("toUnsignedString0", writer);
        reader.accept(pubMethAdapter, 0);
        return writer.toByteArray();
    }

    /**
     * 添加接口 {@code CLONEABLE}
     * 
     * @return
     */
    public byte[] addInterface() {
        addInterfaceAdapter = new AddInterfaceAdapter(writer);
        reader.accept(addInterfaceAdapter, 0);
        return writer.toByteArray();
    }

    public class AddInterfaceAdapter extends ClassVisitor {

        public AddInterfaceAdapter(ClassVisitor cv) {
            super(ASM7, cv);
        }

        @Override
        public void visit(int version, int access, String name,
                String signature, String superName, String[] interfaces) {
            String[] holding = new String[interfaces.length + 1];
            holding[holding.length - 1] = CLONEABLE;
            System.arraycopy(interfaces, 0, holding, 0, interfaces.length);

            cv.visit(V1_5, access, name, signature, superName, holding);
        }

    }

    public class PublicizeMethodAdapter extends ClassVisitor {
        private final String methodName;
        // 字节码以文本的方式展现
        private final TraceClassVisitor tracer;

        public PublicizeMethodAdapter(String methodName, ClassVisitor cv) {
            super(ASM7, cv);
            this.tracer = new TraceClassVisitor(cv, new PrintWriter(System.out));
            this.methodName = methodName;
        }

        @Override
        public MethodVisitor visitMethod(int access,
                String name,
                String desc,
                String signature,
                String[] exceptions) {

            if (name.equals(methodName)) {
                log.info("Visiting unsigned method");
                return tracer.visitMethod(ACC_PUBLIC + ACC_STATIC, name, desc, signature, exceptions);
            }
            return tracer.visitMethod(access, name, desc, signature, exceptions);

        }

        public void visitEnd() {
            tracer.visitEnd();
            System.out.println(tracer.p.getText());
        }

    }

    public class AddFieldAdapter extends ClassVisitor {

        String fieldName;
        int access;
        boolean isFieldPresent;

        public AddFieldAdapter(String fieldName, int access, ClassVisitor cv) {
            super(ASM7, cv);
            this.cv = cv;
            this.access = access;
            this.fieldName = fieldName;
        }

        @Override
        public FieldVisitor visitField(int access, String name, String desc,
                String signature, Object value) {
            if (name.equals(fieldName)) {
                isFieldPresent = true;
            }
            return cv.visitField(access, name, desc, signature, value);
        }

        @Override
        public void visitEnd() {
            if (!isFieldPresent) {
                FieldVisitor fv = cv.visitField(access, fieldName, Type.BOOLEAN_TYPE.toString(), null, null);
                if (fv != null) {
                    fv.visitEnd();
                }
            }
            cv.visitEnd();
        }

    }

}
