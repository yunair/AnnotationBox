package com.air.annotationbox.util
/**
 * Created by air on 3/27/17.
 */

class Utils {
    private Utils() {}

    static String convertClassNameToVMName(String className) {
        return "L" + className.replace('.', '/') + ";"
    }

    static boolean isEmpty(String str) {
        return str == null || str.isEmpty()
    }

}
