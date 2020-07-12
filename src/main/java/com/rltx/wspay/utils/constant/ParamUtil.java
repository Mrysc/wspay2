package com.rltx.wspay.utils.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by  lenovo on 2019/9/11 10:21.
 * Description: No Description
 */

public class ParamUtil {

    public static Map<String, Object> param = new HashMap<String, Object>();

    public static String getParamInfoByKey(String key) {
        return (String) ParamUtil.param.get(key);
    }

}
