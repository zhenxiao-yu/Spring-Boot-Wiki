package com.zhenxiao.wiki.service;

import com.zhenxiao.wiki.entity.Ebook;
import com.zhenxiao.wiki.entity.EbookExample;
import com.zhenxiao.wiki.mapper.EbookMapper;
import com.zhenxiao.wiki.response.EbookRes;
import com.zhenxiao.wiki.util.CopyUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class EbookService {

    @Resource
    private EbookMapper ebookMapper;

    //
    public List<EbookRes> list(Ebook req) {
        EbookExample ebookExample = new EbookExample();
        EbookExample.Criteria criteria = ebookExample.createCriteria();
        //dynamic sql syntax
        if (!ObjectUtils.isEmpty(req.getName())) {
            criteria.andNameLike("%" + req.getName() + "%");
        }
        List<Ebook> ebookList = ebookMapper.selectByExample(ebookExample);

//        //declare new arraylist variable to hold list of ebook responses
//        List<EbookRes> responseList = new ArrayList<>();
//        //iterate through each instance in 'ebooklist' and covert them to 'EbookRes' instances
//        for (Ebook ebook : ebookList) {
//            // EbookRes ebookRes = new EbookRes(); //create a new 'ebookRes' instance
//            //copy properties of each ebook in ebookList to new instances of ebookRes
//            EbookRes ebookRes = CopyUtil.copy(ebook, EbookRes.class);
//            responseList.add(ebookRes);
//        }
        List<EbookRes> list = CopyUtil.copyList(ebookList, EbookRes.class);
        return list;
    }
}
