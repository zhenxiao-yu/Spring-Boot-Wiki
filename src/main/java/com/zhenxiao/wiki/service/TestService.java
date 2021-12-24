package com.zhenxiao.wiki.service;

import com.zhenxiao.wiki.entity.Test;
import com.zhenxiao.wiki.mapper.TestMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TestService {

    @Resource
    private TestMapper testMapper;

    public List<Test> list() {
        return testMapper.list();
    }

}
