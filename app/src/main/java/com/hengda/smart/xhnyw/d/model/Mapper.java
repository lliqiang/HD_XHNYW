package com.hengda.smart.xhnyw.d.model;

/**
 * author: 浩宇张.
 * time: 2017/4/5.
 * desc: 实体类的DTO==>VO的转换方法.
 */

public interface Mapper<T> {
    T transform();
}