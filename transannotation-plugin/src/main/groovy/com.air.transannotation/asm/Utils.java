package com.air.transannotation.asm;

/**
 * Created by air on 3/27/17.
 */

class Utils {
    private Utils() {}

    private static final String KEY_ENCRYPT_ANNO = convertClassNameToVMName("");

    static final String HEADERS_ANNOTATION = convertClassNameToVMName("retrofit2.http.Headers");

    public static boolean needModifyAnnotation(String annotationName) {
        return KEY_ENCRYPT_ANNO.equals(annotationName);
    }

    private static String convertClassNameToVMName(String className) {
        return "L" + className.replace('.', '/') + ";";
    }
}
