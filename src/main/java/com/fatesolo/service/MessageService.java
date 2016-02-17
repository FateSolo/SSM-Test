package com.fatesolo.service;

import com.fatesolo.mapper.MessageMapper;
import com.fatesolo.model.Message;
import com.fatesolo.util.DateTimeUtil;
import com.fatesolo.util.HttpUtil;
import com.fatesolo.util.QuickPager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class MessageService {

    @Resource
    private MessageMapper mapper;

    public boolean add(Message message) {
        message.setCreateTime(DateTimeUtil.getCurrentTimeMillis());
        mapper.add(message);

        message.setCreateTime(DateTimeUtil.millisToDateTime(message.getCreateTime()));
        return message.getId() != 0;
    }

    //获取最新十条数据
    @Cacheable(value = "listCache")
    public List<Message> getList() {
        return mapper.getList(0, 10);
    }

    //手动清除缓存
    @CacheEvict(value = "listCache", allEntries = true)
    public void removeList() {
    }

    //获取腾讯视频真实url
    @Cacheable(value = "urlCache")
    public String getUrl() {
        return HttpUtil.getVideoUrl();
    }

    //为防止url失效, 留出手动清除缓存接口
    @CacheEvict(value = "urlCache", allEntries = true)
    public void removeUrl() {
    }

    //获取分页数据
    public QuickPager<Message> getQuickPager(String currPage, String pageSize) {
        QuickPager<Message> pager = new QuickPager<Message>(currPage, pageSize);

        pager.setTotalRows(mapper.getCount());
        pager.setList(mapper.getList((pager.getCurrPage() - 1) * pager.getPageSize(), pager.getPageSize()));

        return pager;
    }

    //获取所有数据
    public List<Message> getAll() {
        return mapper.getList(0, mapper.getCount());
    }

}
