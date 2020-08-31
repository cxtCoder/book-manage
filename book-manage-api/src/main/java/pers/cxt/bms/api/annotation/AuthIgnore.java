package pers.cxt.bms.api.annotation;

import java.lang.annotation.*;

/**
 * @Author cxt
 * @Date 2020/6/25
 */
@Documented
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthIgnore {

    boolean validate() default true;

}
