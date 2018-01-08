package com.air.annotationbox.asm;

import org.gradle.api.Project;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * Created by air on 3/6/17.
 */

final class ModifierClassWriter extends ClassVisitor {
    private final Project project


    ModifierClassWriter(int api, ClassWriter cv, Project project) {
        super(api, cv)
        this.project = project
    }

    @Override
    MethodVisitor visitMethod(int access, String name, String desc,
                              String signature, String[] exceptions) {
        MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);
        // only concern abstract method, because interface method is abstract
        if ((access & Opcodes.ACC_ABSTRACT) == Opcodes.ACC_ABSTRACT) {
            project.logger.error("method is " + name + " desc is " + desc + " access is " + (access & Opcodes.ACC_ABSTRACT))
            mv = new ModifierMethodWriter(api, mv, name, project)
        }
        return mv
    }


}