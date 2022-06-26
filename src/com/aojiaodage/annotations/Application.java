package com.aojiaodage.annotations;

import java.lang.annotation.*;

@Documented
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = {ElementType.TYPE})
public @interface Application {
    String value();
    String fullNameField() default "classes";
}
