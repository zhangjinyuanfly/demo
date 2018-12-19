package com.zjy.demo.annotation

import java.lang.annotation.RetentionPolicy

@Target(
    AnnotationTarget.ANNOTATION_CLASS,
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER
)
@Retention(RetentionPolicy.CLASS)
annotation class UserApi(val print: String)