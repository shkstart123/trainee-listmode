package com.demo.producer.utils;

import com.alibaba.fastjson.JSONObject;

import java.util.Map;

/**
 * @author shkstart
 * @create 2020-07-29 19:56
 */
public class CommunityUtil {
    //code为编码如403或0为成功，msg如你还未登录，map为业务数据如点赞功能 返回点赞数量，点赞状态，不返回为null；
    public static String getJSONString(int code, String msg, Map<String, Object> map) {
        JSONObject json = new JSONObject();
        json.put("code", code);
        json.put("msg", msg);
        if (map != null) {
            for (String key : map.keySet()) {
                json.put(key, map.get(key));
            }
        }
        return json.toJSONString();
    }

    public static String getJSONString(int code, String msg) {
        return getJSONString(code, msg, null);
    }

    public static String getJSONString(int code) {
        return getJSONString(code, null, null);
    }

    //更多用法：JSONObject.toJSONString(new Object());
}
