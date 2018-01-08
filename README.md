# annotationbox
[![license](http://img.shields.io/badge/license-BSD3-brightgreen.svg?style=flat)](https://github.com/yunair/annotationbox/blob/master/LICENSE)
[ ![Circle CI](https://circleci.com/gh/google/flexbox-layout.svg?style=shield&circle-token=2a42716dfffab73d73c5ce7ed7b3ee620cfa137b) ](https://circleci.com/gh/google/flexbox-layout/tree/master)

This is a annotation converter for android library

# Getting Start

apply the plugin and assign origin annotation and target annotation to your app/build.gradle

such as below:

~~~ gradle
apply plugin: 'com.air.converter'

converter {
    ruleMaps = [
            "com.air.annotationbox.custom.annotations.AGET":"retrofit2.http.GET",
            "com.air.annotationbox.custom.annotations.AHeaders":"retrofit2.http.Headers",
    ]
}
~~~

code on above shows there


# Known Issues

There are some issues:
1. Only support annotation in interface. So, like butterknife annotation is not support


## License
AnnotationBox is under the BSD license. See the [LICENSE](https://github.com/yunair/annotationbox/blob/master/LICENSE) file for details.
