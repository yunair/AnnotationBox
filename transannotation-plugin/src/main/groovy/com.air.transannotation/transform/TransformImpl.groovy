package com.air.transannotation.transform

import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager
import com.xiaoka.network.asm.ModifierClassWriter
import org.apache.commons.codec.digest.DigestUtils
import org.apache.commons.io.FileUtils
import org.gradle.api.Project
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.ClassWriter
import org.objectweb.asm.Opcodes

class TransformImpl extends Transform {
    private final Project project;

    TransformImpl(Project project) {
        this.project = project
    }

    @Override
    String getName() {
        return "TransformApi"
    }

    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS;
    }

    @Override
    Set<QualifiedContent.Scope> getScopes() {
        /*if (variantType == VariantType.LIBRARY) {
            return TransformManager.SCOPE_FULL_LIBRARY;
        }*/

        return TransformManager.SCOPE_FULL_PROJECT;
    }

    @Override
    boolean isIncremental() {
        return false
    }

    @Override
    void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
        def outputProvider = transformInvocation.outputProvider;
       /* for (TransformInput input : transformInvocation.inputs) {
            for (DirectoryInput directoryInput : input.directoryInputs) {
                File dest = outputProvider
                        .getContentLocation(directoryInput.name, directoryInput.contentTypes, directoryInput.scopes, Format.DIRECTORY);
                String buildTypes = directoryInput.file.name
                String productFlavors = directoryInput.file.parentFile.name
                // classes means productFlavors does not exist
                if ("classes".equals(productFlavors)) {
                    productFlavors = ""
                }
                project.logger.error "Copying ${directoryInput.name} to ${dest.absolutePath} \n buildTypes : ${buildTypes} \n productFlavor : ${productFlavors}"
                changeAnnotations(directoryInput.file)
                FileUtils.copyDirectory(directoryInput.file, dest)
            }

            for (JarInput jarInput : input.jarInputs) {
                String destName = jarInput.name;
                def hexName = DigestUtils.md5Hex(jarInput.file.absolutePath);
                if (destName.endsWith(".jar")) {
                    destName = destName.substring(0, destName.length() - 4)
                }
                *//**
                 * 获得输出文件
                 *//*
                File dest = outputProvider.getContentLocation(destName + "_" + hexName, jarInput.contentTypes, jarInput.scopes, Format.JAR);
                FileUtils.copyFile(jarInput.file, dest)
                project.logger.error "Copying ${jarInput.file.absolutePath} to ${dest.absolutePath}"
            }
        }*/
        transformInvocation.inputs.each {
            TransformInput input ->
                input.directoryInputs.each {
                    DirectoryInput directoryInput ->
                        File dest = outputProvider
                                .getContentLocation(directoryInput.name, directoryInput.contentTypes, directoryInput.scopes, Format.DIRECTORY);
                        String buildTypes = directoryInput.file.name
                        String productFlavors = directoryInput.file.parentFile.name
                        // classes means productFlavors does not exist
                        if ("classes".equals(productFlavors)) {
                            productFlavors = ""
                        }
                        //这里进行我们的处理
                        changeAnnotations(directoryInput.file)

                        project.logger.error "Copying ${directoryInput.name} to ${dest.absolutePath} \n buildTypes : ${buildTypes} \n productFlavor : ${productFlavors}"
                        FileUtils.copyDirectory(directoryInput.file, dest)
                }
                input.jarInputs.each {
                    JarInput jarInput ->
                        String destName = jarInput.name;
                        def hexName = DigestUtils.md5Hex(jarInput.file.absolutePath);
                        if (destName.endsWith(".jar")) {
                            destName = destName.substring(0, destName.length() - 4)
                        }

                        /**
                         * 获得输出文件
                         */
                        File dest = outputProvider.getContentLocation(destName + "_" + hexName, jarInput.contentTypes, jarInput.scopes, Format.JAR);

                        //处理jar进行字节码注入处理 TODO

                        FileUtils.copyFile(jarInput.file, dest)
                        project.logger.error "Copying ${jarInput.file.absolutePath} to ${dest.absolutePath}"

                }
        }
//        super.transform(transformInvocation);
//        ClassReader classReader = new ClassReader(in);
//        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);

        //Wrap the ClassWriter with our custom ClassVisitor
//        ClassVisitor cv = new ModifierClassWriter(Opcodes.ASM5, cw);
//        classReader.accept(cv, 0);
    }

    private final FilenameFilter filenameFilter = new FilenameFilter() {
        @Override
        boolean accept(File file, String s) {
            if ("android".equals(s)) {
                return false
            } else if (s.startsWith("R")) {
                return false
            } else if ("BuildConfig.class".equals(s)) {
                return false
            } else {
                return true
            }
        }
    }

    private List<File> getRelatedFiles(File directory) {
        List<File> allFiles = new ArrayList<>()
        File[] files = directory.listFiles(filenameFilter)
        for (File file : files) {
            if (file.directory) {
                allFiles.addAll(getRelatedFiles(file))
            } else {
                allFiles.add(file)
            }
        }

        return allFiles
    }

    private void changeAnnotations(File directory) {
        List<File> files = getRelatedFiles(directory)
        for (File f : files) {
            project.logger.error("after Filter is : " + f.getName())
            FileInputStream fis = new FileInputStream(f)
            ClassReader classReader = new ClassReader(fis);
            ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);

            //Wrap the ClassWriter with our custom ClassVisitor
            ClassVisitor cv = new ModifierClassWriter(Opcodes.ASM5, cw);
            classReader.accept(cv, 0);

            //Write the output to a class file
            DataOutputStream dout = new DataOutputStream(new FileOutputStream(f));
            dout.write(cw.toByteArray());
        }

    }
}