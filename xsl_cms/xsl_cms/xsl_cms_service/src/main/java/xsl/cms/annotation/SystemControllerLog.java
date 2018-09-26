package xsl.cms.annotation;

import java.lang.annotation.*;

/**
 * 自定义注解：记录Controller日志的注解
 */
//该注解使用哪上面生效
@Target( {ElementType.METHOD,ElementType.PARAMETER} )
//该注解生成的字节码文件，保存在JVM中
@Retention( RetentionPolicy.RUNTIME )
//使自定义的注解在API文档中显示
@Documented
public @interface SystemControllerLog {
    String description() default "";
}
