package com.air.transannotation.asm;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.tree.AnnotationNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Created by air on 3/6/17.
 */

final class ModifierAnnotationVisitor extends AnnotationNode {
    private final MethodVisitor mv;

    ModifierAnnotationVisitor(int i, MethodVisitor mv, String desc) {
        super(i, desc);
        this.mv = mv;
    }


    @Override
    public void visitEnd() {
        super.visitEnd();
        System.out.println("av : " + this.toString() + " \t desc : " + desc);
        AnnotationVisitor av = mv.visitAnnotation("", true);
        AnnotationVisitor valueVisitor = av.visitArray("");
        final String value;
        if (values != null) {
            System.out.println(Arrays.deepToString(values.toArray()));
            StringBuilder sb = new StringBuilder();

            List<String> temp = new ArrayList<>((Collection<? extends String>) values.get(1));
            for (String obj : temp) {
                sb.append(obj).append("@");
            }
            value = sb.substring(0, sb.length() - 1);
        } else {
            value = "";
        }

        valueVisitor.visit(null, "" + value);
        valueVisitor.visitEnd();
        av.visitEnd();
    }

}
