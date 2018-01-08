package com.air.annotationbox

import com.air.annotationbox.util.Utils

/**
 * Created by air on 8/5/17.
 */

class PluginExtension {
    public static final String NAME = "annotationbox"

    Map<String, String> ruleMaps = [:] //originAnnotation:targetAnnotation

    boolean needModifyAnnotation(String annotationName) {
        return !Utils.isEmpty(annotationName) && ruleMaps.containsKey(annotationName)
    }

    String targetAnnotation(String annotationName) {
        return ruleMaps.get(annotationName)
    }
}
