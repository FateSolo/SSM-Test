package com.fatesolo.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.Map;

public class Response {

    //使用Nginx进行反向代理, 并管理html, js, css等静态资源文件, 该路径对应了Nginx配置中对应静态资源的location
    public final static String PATH = "/message/";

    public final static String SUCCESS = "000000";

    public final static String FAILURE = "100001";

    public static String success(String msg) {
        return "{\"resCode\":\"" + SUCCESS + "\",\"resMsg\":\"" + msg + "\",\"data\":{}}";
    }

    public static String failure(String msg) {
        return "{\"resCode\":\"" + FAILURE + "\",\"resMsg\":\"" + msg + "\",\"data\":{}}";
    }

    //使用Gson将对象转换为JSON字符串
    public static String toJson(Object obj) {
        Map<String, Object> body = new HashMap<String, Object>();

        body.put("resCode", SUCCESS);
        body.put("resMsg", "成功");
        body.put("data", obj);

        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        return gson.toJson(body);
    }

}
