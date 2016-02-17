package com.fatesolo.util;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class HttpUtil {

    //发起Http Get请求
    public static String httpRequest(String url) throws Exception {
        StringBuilder response = new StringBuilder();
        String line;

        URL realUrl = new URL(url);
        URLConnection connection = realUrl.openConnection();
        connection.connect();
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        while ((line = in.readLine()) != null) {
            response.append(line);
        }
        in.close();

        return response.toString();
    }

    //获取包含腾讯视频真实地址的xml, 使用Dom4j解析出真实url, vid可自行更换
    public static String getVideoUrl() {
        String response = "";
        try {
            response = HttpUtil.httpRequest("http://vv.video.qq.com/geturl?vid=f0019r1sytg");
            Document document = DocumentHelper.parseText(response);
            response = document.getRootElement().element("vd").element("vi").element("url").getText();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

}
