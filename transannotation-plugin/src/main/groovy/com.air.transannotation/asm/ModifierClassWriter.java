package com.air.transannotation.asm;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * Created by air on 3/6/17.
 */

public class ModifierClassWriter extends ClassVisitor {

    public ModifierClassWriter(int api, ClassWriter cv) {
        super(api, cv);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc,
                                     String signature, String[] exceptions) {

        MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);
        // only concern abstract method, because interface method is abstract
        if((access & Opcodes.ACC_ABSTRACT) == Opcodes.ACC_ABSTRACT){
            mv = new ModifierMethodWriter(api, mv, name);
        }
        return mv;
    }
}