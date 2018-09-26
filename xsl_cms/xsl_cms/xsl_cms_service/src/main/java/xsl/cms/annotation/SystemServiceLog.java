package xsl.cms.annotation;

import java.lang.annotation.*;

/**
 *  自定义注解：记录Service异常日志的注解
 */
@Target({ElementType.METHOD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SystemServiceLog {
    String description() default "";
}
