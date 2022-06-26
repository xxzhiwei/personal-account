package com.aojiaodage.annotations;

import java.lang.annotation.*;

@Documented
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = {ElementType.TYPE})
public @interface Service {
    String value() default "";
    int order() default 1;
    String name() default "";
}
