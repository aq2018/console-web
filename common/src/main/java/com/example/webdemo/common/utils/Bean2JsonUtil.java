package com.example.webdemo.common.utils;

import java.lang.reflect.Field;
import java.util.Date;

/**
 * 类转为json报文
 *
 * @author aq
 * @since 2019/5/30 15:59
 */
public class Bean2JsonUtil {
    public static void main(String[] args) {
        Demo d = new Demo();
        // bean2Json(Demo.class);
        // getBeanField(CreditFundSerialRecordDto.class);
        // setAndGetMethod(Demo.class, "b"); // setM(m.getM())
        bean2BuildQuery(Demo.class); // DemoQuery.builder().build();
    }

    private static void bean2BuildQuery(Class clazz) {
        Field[] fields = clazz.getDeclaredFields();
        String simpleName = clazz.getSimpleName();
        System.out.println(simpleName);
        StringBuilder builder = new StringBuilder();
        builder.append(simpleName);
        builder.append(".builder()\n");
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            builder.append("\t.");
            builder.append(field.getName());
            builder.append("()\n");
        }
        builder.append(".build()");
        System.out.println(builder.toString());
    }

    private static void bean2Json(Class clazz) {
        Field[] fields = clazz.getDeclaredFields();
        StringBuilder builder = new StringBuilder();
        builder.append("{\n");
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            builder.append("\t\"");
            builder.append(field.getName());
            builder.append("\" : ");
            getData4Type(builder, field);
            if (i < fields.length - 1) {
                builder.append(",");
            }
            builder.append("\n");
        }
        builder.append("}");
        System.out.println(builder.toString());

    }

    private static void setAndGetMethod(Class clazz, String b) {
        Field[] fields = clazz.getDeclaredFields();
        String simpleName = clazz.getSimpleName();
        String className = String.valueOf(simpleName.charAt(0))
            .toLowerCase() + simpleName.substring(1);
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            String bean = field.getName();
            StringBuilder builder = new StringBuilder(className + ".set");
            builder.append(String.valueOf(bean.charAt(0))
                .toUpperCase());
            builder.append(bean.substring(1));
            builder.append("(");
            builder.append(b + ".get");
            builder.append(String.valueOf(bean.charAt(0))
                .toUpperCase());
            builder.append(bean.substring(1));
            builder.append("(");
            builder.append(")");
            builder.append(");");
            System.out.println(builder.toString());
        }
    }

    private static void getBeanField(Class clazz) {
        Field[] fields = clazz.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            System.out.println(field.getName());
        }

    }

    private static void bean2Set(Class<?> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        String clazzName = clazz.getSimpleName();
        String bean = String.valueOf(clazzName.charAt(0))
            .toLowerCase() + clazzName.substring(1);
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            String fieldName = field.getName();
            builder.append(bean);
            builder.append(".set");
            builder.append(String.valueOf(fieldName.charAt(0))
                .toUpperCase());
            builder.append(fieldName.substring(1));
            builder.append("(");
            getData4Type4Set(builder, field);
            builder.append(");");
            builder.append("\n");
        }
        System.out.println(builder.toString());
    }

    /**
     * 转换req 默认赋初始值为基本类型
     *
     * @param builder
     * @param field
     */
    private static void getData4Type(StringBuilder builder, Field field) {
        if ("class java.lang.Integer".equals(field.getType()
            .toString()) || "int".equals(field.getType()
            .toString())) {
            builder.append(1);
        } else if ("class java.lang.Double".equals(field.getType()
            .toString()) || "double".equals(field.getType()
            .toString())) {
            builder.append(1.0d);
        } else if ("class java.lang.Boolean".equals(field.getType()
            .toString()) || "boolean".equals(field.getType()
            .toString())) {
            builder.append(true);
        } else if ("class java.lang.Long".equals(field.getType()
            .toString()) || "long".equals(field.getType()
            .toString())) {
            builder.append(1L);
        } else if ("class java.util.Date".equals(field.getType()
            .toString())) {
            builder.append("\"");
            builder.append(DateUtil.getDate(new Date(), DateUtil.DATE_FORMAT_2));
            builder.append("\"");
        } else {
            builder.append("\"");
            builder.append("1");
            builder.append("\"");
        }
    }

    /**
     * 转换req 默认赋初始值为new date()
     *
     * @param builder
     * @param field
     */
    private static void getData4Type4Set(StringBuilder builder, Field field) {
        if ("class java.lang.Integer".equals(field.getType()
            .toString()) || "int".equals(field.getType()
            .toString())) {
            builder.append(1);
        } else if ("class java.lang.Double".equals(field.getType()
            .toString()) || "double".equals(field.getType()
            .toString())) {
            builder.append(1.0d);
        } else if ("class java.lang.Boolean".equals(field.getType()
            .toString()) || "boolean".equals(field.getType()
            .toString())) {
            builder.append(true);
        } else if ("class java.lang.Long".equals(field.getType()
            .toString()) || "long".equals(field.getType()
            .toString())) {
            builder.append(1L);
        } else if ("class java.util.Date".equals(field.getType()
            .toString())) {
            builder.append("new Date()");
        } else if ("class java.math.BigDecimal".equals(field.getType()
            .toString())) {
            builder.append("new BigDecimal(1)");
        } else {
            builder.append("\"");
            builder.append("1");
            builder.append("\"");
        }
    }
}
