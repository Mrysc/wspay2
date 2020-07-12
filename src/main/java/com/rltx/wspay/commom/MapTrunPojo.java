package com.rltx.wspay.commom;


import java.lang.reflect.Field;
import java.util.TreeMap;

public class MapTrunPojo {

    public static TreeMap<String, String> object2Map(Object obj) {
        TreeMap<String, String> map = new TreeMap<>();
        if (obj == null) {
            return map;
        }
        Class clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();
        try {
            for (Field field : fields) {
                field.setAccessible(true);
                map.put(captureName(field.getName()), (String) field.get(obj));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }


    //接口文档 字段首字母大写  实例中的成员变量 命名 驼峰小写  需要对首字符转换大写字母处理
    public static String captureName(String name) {

        char[] cs = name.toCharArray();
        cs[0] -= 32;
        return String.valueOf(cs);

    }


}



