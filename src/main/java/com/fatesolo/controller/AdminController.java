package com.fatesolo.controller;

import com.fatesolo.model.User;
import com.fatesolo.service.LoginService;
import com.fatesolo.service.MessageService;
import com.fatesolo.socket.WebSocketManager;
import com.fatesolo.util.DateTimeUtil;
import com.fatesolo.util.ExcelUtil;
import com.fatesolo.util.Response;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/admin", produces = "application/json;charset=UTF-8")
public class AdminController {

    @Resource
    private LoginService service;

    @Resource
    private MessageService service2;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public void login(HttpServletRequest request,
                      HttpServletResponse response,
                      User user) throws IOException {
        if (service.login(user.getUsername(), user.getPassword())) {
            HttpSession session = request.getSession();
            session.setAttribute("user", user);

            response.sendRedirect(Response.PATH + "dashboard.html");
        } else {
            response.sendRedirect(Response.PATH + "login.html?error=1");
        }
    }

    @RequestMapping(value = "/message", method = RequestMethod.GET)
    @ResponseBody
    public String getList(@RequestParam(value = "currPage", required = false) String currPage,
                          @RequestParam(value = "pageSize", required = false) String pageSize) {
        return Response.toJson(service2.getQuickPager(currPage, pageSize));
    }

    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public void download(HttpServletResponse response) throws IOException {
        String fileName = "留言列表 " + DateTimeUtil.millisToDate(DateTimeUtil.getCurrentTimeMillis());

        short[] width = {7, 20, 100, 35};
        String[] columnNames = {"ID", "姓名", "留言", "时间"};
        String[] keys = {"id", "name", "message" ,"createTime"};
        List<Map<String, Object>> list = ExcelUtil.createExcelRecord(service2.getAll());

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ExcelUtil.createWorkBook(list, width, keys, columnNames).write(os);

        byte[] content = os.toByteArray();
        InputStream is = new ByteArrayInputStream(content);

        response.reset();
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + new String((fileName + ".xls").getBytes(), "iso-8859-1"));

        ServletOutputStream out = response.getOutputStream();
        BufferedInputStream bis = new BufferedInputStream(is);
        BufferedOutputStream bos = new BufferedOutputStream(out);

        byte[] buff = new byte[2048];
        int bytesRead;

        while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
            bos.write(buff, 0, bytesRead);
        }

        bis.close();
        bos.close();
    }

    //为防止腾讯视频url失效而设的手动清除缓存接口
    @RequestMapping(value = "/remove", method = RequestMethod.GET)
    @ResponseBody
    public String removeUrl() {
        service2.removeUrl();
        return Response.success("RemoveUrlCacheSuccess");
    }

    //查看当前建立socket连接的用户数量
    @RequestMapping(value = "/size", method = RequestMethod.GET)
    @ResponseBody
    public String getSize() {
        return Response.success(String.valueOf(WebSocketManager.getSize()));
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public void logout(HttpServletRequest request,
                       HttpServletResponse response) throws IOException {
        request.getSession().invalidate();
        response.sendRedirect(Response.PATH + "login.html");
    }

}
