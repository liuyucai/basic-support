package com.lyc.simple.utils;

import javax.persistence.Column;
import javax.persistence.Id;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * @author: liuyucai
 * @Created: 2023/3/10 9:17
 * @Description:
 */
public class DbTableUtil {

    public static String getPrimaryKeyName(Class clazz) {
        List<Field> fields = getAllFields(clazz);
        Iterator var2 = fields.iterator();

        Field field;
        Id id;
        do {
            if (!var2.hasNext()) {
                return null;
            }

            field = (Field)var2.next();
            id = (Id)field.getAnnotation(Id.class);
        } while(id == null);

        Column column = (Column)field.getAnnotation(Column.class);
        String idName = column.name();
        if (idName.contains("_")) {
            StringBuilder sb = new StringBuilder();
            String[] strs = idName.split("_");
            sb.append(strs[0]);

            for(int i = 1; i < strs.length; ++i) {
                sb.append(strs[i].substring(0, 1).toUpperCase() + strs[i].substring(1));
            }

            idName = sb.toString();
        }

        return idName;
    }

    private static List<Field> getAllFields(Class<?> clazz) {
        List<Field> fieldList = new ArrayList();
        Field[] dFields = clazz.getDeclaredFields();
        if (null != dFields && dFields.length > 0) {
            fieldList.addAll(Arrays.asList(dFields));
        }

        Class<?> superClass = clazz.getSuperclass();
        if (superClass == Object.class) {
            return Arrays.asList(dFields);
        } else {
            List<Field> superFields = getAllFields(superClass);
            if (null != superFields && !superFields.isEmpty()) {
                superFields.stream().filter((field) -> {
                    return !fieldList.contains(field);
                }).forEach((field) -> {
                    fieldList.add(field);
                });
            }

            return fieldList;
        }
    }
}
