package com.air.annotationbox.asm

import org.objectweb.asm.AnnotationVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.tree.AnnotationNode

/**
 * Created by air on 3/6/17.
 */

final class ModifierAnnotationVisitor extends AnnotationNode {
    private final MethodVisitor mv
    private final String newDesc
    private final boolean visitParams
    private final int parameter

    static ModifierAnnotationVisitor newParamsVisitor(int api, MethodVisitor mv, String desc, String newDesc, int parameter) {
        return new ModifierAnnotationVisitor(api, mv, desc, newDesc, parameter, true)
    }

    static ModifierAnnotationVisitor newMethodsVisitor(int api, MethodVisitor mv, String desc, String newDesc) {
        return new ModifierAnnotationVisitor(api, mv, desc, newDesc, -1, false)
    }

    private ModifierAnnotationVisitor(int api, MethodVisitor mv, String desc, String newDesc, int parameter, boolean visitParams) {
        super(api, desc);
        this.mv = mv;
        this.newDesc = newDesc;
        this.visitParams = visitParams
        this.parameter = parameter
    }


    @Override
    void visitEnd() {
        System.out.println("av : " + this.toString() + " \t desc : " + desc)
        final AnnotationVisitor av
        if (visitParams) {
            av = mv.visitParameterAnnotation(parameter, newDesc, true)
        } else {
            av = mv.visitAnnotation(newDesc, true)
        }
        convert(av)
    }

    private void convert(AnnotationVisitor av) {
        if (values != null) {
            System.out.println(Arrays.deepToString(values.toArray()))
            for (int i = 0; i < values.size(); i += 2) {
                def valueIndex = i + 1
                if (values[valueIndex] instanceof Collection<? extends String>) {
                    List<String> temp = new ArrayList<>((Collection<? extends String>) values[valueIndex])
                    AnnotationVisitor valueVisitor = av.visitArray(values[i])
                    for (item in temp) {
                        valueVisitor.visit(null, item)
                    }
                    valueVisitor.visitEnd()
                } else {
                    av.visit(values[i], values[valueIndex])
                }
            }

        }

        av.visitEnd()
    }

}
