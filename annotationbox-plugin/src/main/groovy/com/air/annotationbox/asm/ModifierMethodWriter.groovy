package com.air.annotationbox.asm;


import org.gradle.api.Project;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.MethodVisitor;

/**
 * Created by air on 3/6/17.
 */

final class ModifierMethodWriter extends MethodVisitor {
    private final String methodName
    def configuration
    def final log

    ModifierMethodWriter(int api, MethodVisitor mv, String methodName, Project project) {
        super(api, mv)
        this.methodName = methodName
        configuration = project.converter
        log = project.logger
        log.error("RuleMap" + configuration.ruleMaps)
    }

    @Override
    AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        if (configuration.needModifyAnnotation(desc)) {
            return ModifierAnnotationVisitor.newMethodsVisitor(api, mv, desc, configuration.targetAnnotation(desc));
        } else {
            return super.visitAnnotation(desc, visible)
        }
    }

    @Override
    AnnotationVisitor visitParameterAnnotation(int parameter, String desc, boolean visible) {
        if (configuration.needModifyAnnotation(desc)) {
            return ModifierAnnotationVisitor.newParamsVisitor(api, mv, desc, configuration.targetAnnotation(desc), parameter)
        } else {
            return super.visitParameterAnnotation(parameter, desc, visible)
        }
    }
}
