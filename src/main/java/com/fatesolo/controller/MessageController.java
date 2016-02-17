package com.fatesolo.controller;

import com.fatesolo.model.Message;
import com.fatesolo.service.MessageService;
import com.fatesolo.socket.WebSocketManager;
import com.fatesolo.util.Response;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/message", produces = "application/json;charset=UTF-8")
public class MessageController {

    @Resource
    private MessageService service;

    //当有数据更新时, 清空缓存, 并向所有建立socket连接的用户发送实时数据
    @RequestMapping(value = "", method = RequestMethod.POST)
    public String add(Message message) {
        if (service.add(message)) {
            service.removeList();
            WebSocketManager.sendMessage(Response.toJson(message));

            return Response.success("AddSuccess");
        } else {
            return Response.failure("AddFailure");
        }
    }

    //取得前十条数据和腾讯视频真实地址
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String getList() {
        Map<String, Object> map = new HashMap<String, Object>();

        map.put("list", service.getList());
        map.put("url", service.getUrl());

        return Response.toJson(map);
    }

}
