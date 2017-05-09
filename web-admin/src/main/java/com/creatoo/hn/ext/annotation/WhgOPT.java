package com.creatoo.hn.ext.annotation;

import com.creatoo.hn.ext.emun.EnumOptType;
import com.creatoo.hn.ext.emun.EnumState;

import java.lang.annotation.*;

/**
 * 系统操作注解
 * Created by wangxl on 2017/4/25.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface WhgOPT {
    String[] optDesc();
    EnumOptType optType();

    String[] valid() default {"true"};
}
