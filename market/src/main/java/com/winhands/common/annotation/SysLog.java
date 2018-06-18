package com.winhands.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 系统日志注解
 * @项目 oauth 
 * @author yuanl 
 * 2017年7月28日 
 * @Description
 */
//修饰的注解表示该注解只能用来修饰在方法上
@Target(ElementType.METHOD)
//表示注解的信息被保留在class文件(字节码文件)中当程序编译时，会被虚拟机保留在运行时，
//所以他们可以用反射的方式读取
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SysLog {

	String value() default "";
}
