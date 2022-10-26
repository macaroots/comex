package br.com.alura.comex.dto;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public abstract class Dto<T, K> {
    public abstract T toEntity();
    public Object getId() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
        T entity = toEntity();
        Method method = entity.getClass().getDeclaredMethod("getId");
        return method.invoke(entity);
    }
}