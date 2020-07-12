package com.rltx.wspay.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MapChangeKay {

    public static Map<String, Object> transformUpperCase(Map<String, Object> orgMap)
    {
        Map<String, Object> resultMap = new HashMap<>();

        if (orgMap == null || orgMap.isEmpty())
        {
            return resultMap;
        }

        Set<String> keySet = orgMap.keySet();
        for (String key : keySet)
        {
            String first = key.substring(0, 1);
            String after = key.substring(1); //substring(1),获取索引位置1后面所有剩余的字符串
            first = first.toLowerCase();
            String newKey = first+after;
            newKey = newKey.replace("_", "");
            resultMap.put(newKey, orgMap.get(key));
        }

        return resultMap;
    }

}
