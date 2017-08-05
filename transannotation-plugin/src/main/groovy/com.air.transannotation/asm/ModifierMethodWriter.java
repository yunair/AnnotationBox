package com.air.transannotation.asm;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.MethodVisitor;

/**
 * Created by air on 3/6/17.
 */

final class ModifierMethodWriter extends MethodVisitor {
    private final String methodName;

    ModifierMethodWriter(int api, MethodVisitor mv, String methodName) {
        super(api, mv);
        this.methodName = methodName;
    }

    @Override
    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        System.out.println("desc : " + desc + " visible : " + visible);

        if (Utils.needModifyAnnotation(desc)) {
            return new ModifierAnnotationVisitor(api, mv, desc);
        } else {
            return super.visitAnnotation(desc, visible);
        }
    }

    @Override
    public AnnotationVisitor visitParameterAnnotation(int parameter, String desc, boolean visible) {
        return super.visitParameterAnnotation(parameter, desc, visible);
    }
}
