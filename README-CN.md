# annotationbox

这是一个用来做Annotation转换的library

# 使用方式

将 annotationbox-plugin 作为依赖添加到根 build.gradle 文件中:

~~~ gradle
buildscript {
    dependencies {
        classpath 'com.air:annotationbox-plugin:0.5'
    }
}
~~~

apply the plugin and 设置原始annotation和目标annotation在app/build.gradle文件中，类似如下代码：

~~~ gradle
apply plugin: 'com.air.annotationbox'

annotationbox {
    ruleMaps = [
            "com.air.annotationbox.custom.annotations.AGET":"retrofit2.http.GET",
            "com.air.annotationbox.custom.annotations.AHeaders":"retrofit2.http.Headers",
    ]
}
~~~

然后，当你build app的时候，代码中所有的ruleMaps中key所对应的注解会全部变成ruleMaps中value对应的注解。

这样就完成了注解的封装。

当你遇到第三方库升级的时候，修改注解包名的时候，这就特别有用。
例如retrofit1升级到retrofit2，遇到过的团队应该有同样的感慨。
