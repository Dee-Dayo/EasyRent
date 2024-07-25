package com.semicolon.EaziRent.utils;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.util.HashSet;
import java.util.Set;

public class EaziUtils {

    public static void copyNonNullProperties(Object source, Object target) {
        BeanUtils.copyProperties(source, target, getNullAttributes(source));
    }

    private static String[] getNullAttributes(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        Set<String> nullAttributes = new HashSet<>();
        for(java.beans.PropertyDescriptor descriptor : src.getPropertyDescriptors()) {
            Object value = src.getPropertyValue(descriptor.getName());
            if(value == null) nullAttributes.add(descriptor.getName());
        }
        String[] result = new String[nullAttributes.size()];
        return nullAttributes.toArray(result);
    }

}
