package cash.spring.common.combineAnnotation;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.lang.annotation.*;

/**
 *
 * @author xuchao
 * 2017年4月23日 上午12:17:07
 *
 * 可以注解到别的注解上的注解--->元注解
 * 被注解的注解 --->组合注解
 *
 * @Configuration 和  @ComponentScan 注解,组合成 注解@cashConfiguration
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented

@Configuration
@ComponentScan
public @interface cashConfiguration {

	String[] value() default {};

}
