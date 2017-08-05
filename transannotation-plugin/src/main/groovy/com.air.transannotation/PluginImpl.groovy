package com.air.transannotation

import com.android.build.gradle.AppPlugin
import com.android.build.gradle.BaseExtension
import com.android.build.gradle.LibraryPlugin
import com.xiaoka.network.transform.TransformImpl
import org.gradle.api.Plugin
import org.gradle.api.Project

public class PluginImpl implements Plugin<Project> {

    @Override
    void apply(Project project) {
//        project.plugins.hasPlugin("com.android.application")
        def isApp = project.plugins.hasPlugin(AppPlugin)
        def isLibrary = project.plugins.hasPlugin(LibraryPlugin)
        def isAndroid = isApp || isLibrary
        if (!isAndroid) {
            throw new IllegalArgumentException("You must apply app plugin or library plugin")
        }

        def android = project.extensions.getByType(BaseExtension)
        def transform = new TransformImpl(project)
        android.registerTransform(transform)

        project.afterEvaluate {
            project.android.applicationVariants.each { variant ->
                def proguardTask = project.tasks.findByName("transformClassesAndResourcesWithProguardFor${variant.name.capitalize()}")
                if (proguardTask) {
                    project.logger.error "proguard=>${variant.name.capitalize()}"

                    proguardTask.inputs.files.files.each { File file->
                        project.logger.error "file inputs=>${file.absolutePath}"
                    }

                    proguardTask.outputs.files.files.each { File file->
                        project.logger.error "file outputs=>${file.absolutePath}"
                    }
                }

                def dexTask = project.tasks.findByName("transformClassesWithDexFor${variant.name.capitalize()}")
                if (dexTask) {
                    project.logger.error "dex=>${variant.name.capitalize()}"

                    dexTask.inputs.files.files.each { File file->
                        project.logger.error "file inputs=>${file.absolutePath}"
                    }

                    dexTask.outputs.files.files.each { File file->
                        project.logger.error "file outputs=>${file.absolutePath}"
                    }
                }

                def testTask = project.tasks.findByName("transformClassesWithTransformApiFor${variant.name.capitalize()}")

                if (testTask) {

                    Set<File> testTaskInputFiles = testTask.inputs.files.files
                    Set<File> testTaskOutputFiles = testTask.inputs.files.files

                    project.logger.error "Name:transformClassesWithTransformImpl=====>${testTask.name} input"
                    testTaskInputFiles.each { inputFile ->
                        def path = inputFile.absolutePath
                        project.logger.error path
                    }

                    project.logger.error "Name:transformClassesWithTransformImpl=====>${testTask.name} output"
                    testTaskOutputFiles.each { inputFile ->
                        def path = inputFile.absolutePath
                        project.logger.error path
                    }
                }
            }


        }
    }
}