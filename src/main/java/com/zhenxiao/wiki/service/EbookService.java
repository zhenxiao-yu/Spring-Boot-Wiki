package com.zhenxiao.wiki.service;

import com.zhenxiao.wiki.entity.Ebook;
import com.zhenxiao.wiki.entity.EbookExample;
import com.zhenxiao.wiki.mapper.EbookMapper;
import com.zhenxiao.wiki.response.EbookRes;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class EbookService {

    @Resource
    private EbookMapper ebookMapper;

    //
    public List<EbookRes> list(Ebook req) {
        EbookExample ebookExample = new EbookExample();
        EbookExample.Criteria criteria = ebookExample.createCriteria();
        criteria.andNameLike("%" + req.getName() + "%");
        List<Ebook> ebookList = ebookMapper.selectByExample(ebookExample);

        //declare new arraylist variable to hold list of ebook responses
        List<EbookRes> responseList = new ArrayList<>();
        //iterate through each instance in 'ebooklist' and covert them to 'EbookRes' instances
        for (Ebook ebook : ebookList) {
            //create a new 'ebookRes' instance
            EbookRes ebookRes = new EbookRes();
            //copy properties of each ebook in ebookList to new instances of ebookRes
            BeanUtils.copyProperties(ebook, ebookRes);
            //add them to new ebook response list
            responseList.add(ebookRes);
        }

        return responseList;
    }
}
