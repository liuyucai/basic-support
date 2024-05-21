package com.lyc.simple.utils;

import com.lyc.simple.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author: liuyucai
 * @Created: 2023/3/26 20:40
 * @Description:
 */
public class ConditionUtils {


    public static Annotation getAnnotation(Field field) {
        Annotation annotation = isEqual(field);
        if (annotation != null) {
            return annotation;
        } else {
            annotation = isIn(field);
            if (annotation != null) {
                return annotation;
            } else {
                annotation = isLike(field);
                if (annotation != null) {
                    return annotation;
                } else {
                    annotation = isBetween(field);
                    return annotation != null ? annotation : annotation;
                }
            }
        }
    }

    public static Annotation isEqual(Field field) {
        return field.isAnnotationPresent(Equal.class) ? field.getAnnotation(Equal.class) : null;
    }

    public static Annotation isIn(Field field) {
        return field.isAnnotationPresent(In.class) ? field.getAnnotation(In.class) : null;
    }

    public static Annotation isLike(Field field) {
        return field.isAnnotationPresent(Like.class) ? field.getAnnotation(Like.class) : null;
    }

    public static Annotation isBetween(Field field) {
        return field.isAnnotationPresent(Between.class) ? field.getAnnotation(Between.class) : null;
    }

    public static List<ConditionWapper> findObjectCondition(Object object) {
        List<ConditionWapper> wappers = new ArrayList();
        Field[] fields = object.getClass().getDeclaredFields();
        Arrays.asList(fields).forEach((field) -> {
            if (!field.getName().equals("serialVersionUID")) {
                if (!field.isAnnotationPresent(ConditionIgnore.class)) {
                    try {
                        field.setAccessible(true);
                        Object value = field.get(object);
                        Annotation annotation = getAnnotation(field);
                        Class<?> annotationType = Equal.class;
                        if (annotation != null) {
                            annotationType = annotation.annotationType();
                        }

                        if (annotationType.getName().equals(Between.class.getName())) {
                            try {
                                Between between = (Between)field.getAnnotation(Between.class);
                                Field startField = object.getClass().getDeclaredField(between.startAttribute());
                                Field endField = object.getClass().getDeclaredField(between.endAttribute());
                                startField.setAccessible(true);
                                endField.setAccessible(true);
                                Object start = startField.get(object);
                                Object end = endField.get(object);
                                if (start == null && end == null) {
                                    return;
                                }

                                ConditionWapper conditionWapper = new ConditionWapper();
                                wappers.add(conditionWapper);
                                conditionWapper.setFieldName(field.getName());
                                conditionWapper.setStartValue(start);
                                conditionWapper.setEndValue(end);
                                conditionWapper.setConditionType(annotationType);
                            } catch (NoSuchFieldException var12) {
                                var12.printStackTrace();
                            }

                            return;
                        }

                        if (value == null) {
                            return;
                        }

                        ConditionWapper conditionWapperx = new ConditionWapper();
                        wappers.add(conditionWapperx);
                        conditionWapperx.setFieldName(field.getName());
                        conditionWapperx.setDataType(field.getGenericType());
                        conditionWapperx.setValue(value);
                        conditionWapperx.setConditionType(annotationType);
                    } catch (IllegalAccessException var13) {
                        var13.printStackTrace();
                    }

                }
            }
        });
        return wappers;
    }

    public static List<Predicate> toPredicate(List<ConditionWapper> wappers, Root<?> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        List<Predicate> predicates = new ArrayList();
        wappers.forEach((wapper) -> {
            String var5 = wapper.getConditionType().getSimpleName();
            byte var6 = -1;
            switch(var5.hashCode()) {
                case 2373:
                    if (var5.equals("In")) {
                        var6 = 1;
                    }
                    break;
                case 2368439:
                    if (var5.equals("Like")) {
                        var6 = 2;
                    }
                    break;
                case 67204884:
                    if (var5.equals("Equal")) {
                        var6 = 0;
                    }
                    break;
                case 1448018920:
                    if (var5.equals("Between")) {
                        var6 = 3;
                    }
            }

            switch(var6) {
                case 0:
                    predicates.add(buildEqual(wapper, root, query, builder));
                    break;
                case 1:
                    predicates.add(buildIn(wapper, root, query, builder));
                    break;
                case 2:
                    predicates.add(buildLike(wapper, root, query, builder));
                    break;
                case 3:
                    predicates.add(buildBetween(wapper, root, query, builder));
            }

        });
        return predicates;
    }

    private static Predicate buildEqual(ConditionWapper wapper, Root<?> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        return builder.equal(root.get(wapper.getFieldName()), wapper.getValue());
    }

    private static Predicate buildIn(ConditionWapper wapper, Root<?> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        javax.persistence.criteria.CriteriaBuilder.In<Object> in = builder.in(root.get(wapper.getFieldName()));
        String[] values = ((String)wapper.getValue()).split(",");
        Arrays.asList(values).forEach((value) -> {
            in.value(value);
        });
        return in;
    }

    private static Predicate buildLike(ConditionWapper wapper, Root<?> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        return builder.like(root.get(wapper.getFieldName()), "%" + wapper.getValue() + "%");
    }

    private static Predicate buildBetween(ConditionWapper wapper, Root<?> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        Date startTime = (Date)wapper.getStartValue();
        Date endTime = (Date)wapper.getEndValue();
        String startClock = " 00:00:00";
        String endClock = " 23:59:59";
        String dateStr;
//        if (startTime != null && endTime != null) {
//            String startTimeStr = DateUtils.formatDate(startTime, new Object[]{"yyyy-MM-dd"});
//            dateStr = DateUtils.formatDate(endTime, new Object[]{"yyyy-MM-dd"});
//
//            try {
//                startTime = DateUtils.parseDate(startTimeStr + startClock, new String[]{"yyyy-MM-dd HH:mm:ss"});
//                endTime = DateUtils.parseDate(dateStr + endClock, new String[]{"yyyy-MM-dd HH:mm:ss"});
//            } catch (ParseException var11) {
//                var11.printStackTrace();
//            }
//
//            return builder.between(root.get(wapper.getFieldName()), startTime, endTime);
//        } else {
//            Date dateTime = startTime != null ? startTime : endTime;
//            dateStr = DateUtils.formatDate(dateTime, new Object[]{"yyyy-MM-dd"});
//
//            try {
//                startTime = DateUtils.parseDate(dateStr + startClock, new String[]{"yyyy-MM-dd HH:mm:ss"});
//                endTime = DateUtils.parseDate(dateStr + endClock, new String[]{"yyyy-MM-dd HH:mm:ss"});
//            } catch (ParseException var12) {
//                var12.printStackTrace();
//            }
//
//            return builder.between(root.get(wapper.getFieldName()), startTime, endTime);
//        }

        return builder.between(root.get(wapper.getFieldName()), startTime, endTime);
    }



}
