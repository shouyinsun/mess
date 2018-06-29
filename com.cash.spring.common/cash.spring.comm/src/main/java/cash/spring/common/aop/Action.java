package cash.spring.common.aop;

import java.lang.annotation.*;

/**
 *
 * @author cash
 * @date 2017年4月16日 下午5:36:58
 * @decription 自定义注解 ,基于此注解拦截
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Action {
	String name() default "";
}
