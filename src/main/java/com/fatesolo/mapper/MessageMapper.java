package com.fatesolo.mapper;

import com.fatesolo.model.Message;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MessageMapper {

    void add(Message message);

    int getCount();

    /*
     * @param m : 从第m+1条数据开始获取
     * @param n : 获取n条数据
     */
    List<Message> getList(@Param(value="m") int m, @Param(value="n") int n);

}
