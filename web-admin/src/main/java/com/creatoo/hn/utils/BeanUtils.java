package com.creatoo.hn.utils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;

/**
 * 操作Bean的辅助类
 * @author wangxl
 *
 */
public class BeanUtils {
    /**
     * 合并两个并的属性，将destination中不为空的属性赋值到target对象中
     * @param target 目标对象
     * @param destination 被合并对象
     * @throws Exception
     */
    public static <M> void merge(M target, M destination) throws Exception {
        BeanInfo beanInfo = Introspector.getBeanInfo(destination.getClass());

        // Iterate over all the attributes
        for (PropertyDescriptor descriptor : beanInfo.getPropertyDescriptors()) {

            // Only copy writable attributes
            if (descriptor.getWriteMethod() != null) {
                Object originalValue = descriptor.getReadMethod().invoke(destination);

                // Only copy values values where the destination values is null
                if (originalValue != null) {
                    //Object defaultValue = descriptor.getReadMethod().invoke(destination);
                    descriptor.getWriteMethod().invoke(target, originalValue);
                }

            }
        }
    }
}
