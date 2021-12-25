package com.zhenxiao.wiki.util;

import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

// utility class used for copying one object to another
public class CopyUtil {
    // copy method for a single object
    public static <T> T copy(Object source, Class<T> clazz) {
        //return null if source object is null
        if (source == null) {
            return null;
        }
        T obj = null;
        try {
            //create new object to copy to
            obj = clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        //use BeanUtils.copyProperties to copy source object to created object
        BeanUtils.copyProperties(source, obj);
        //return coped object
        return obj;
    }

    // copy method for a list of objects
    public static <T> List<T> copyList(List source, Class<T> clazz) {
        //declare new arraylist for storing copied object
        List<T> target = new ArrayList<>();
        // when source list is not empty
        if (!CollectionUtils.isEmpty(source)){
            //iterate through every object in source
            for (Object c: source) {
                // call the copy method and copy each object to new object
                T obj = copy(c, clazz);
                //add newly copped object to the new arraylist
                target.add(obj);
            }
        }
        //return copied list
        return target;
    }
}
