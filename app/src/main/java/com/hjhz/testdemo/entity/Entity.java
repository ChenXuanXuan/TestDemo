package com.hjhz.testdemo.entity;

import com.hjhz.testdemo.entity.Base;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 实体类
 * 
 * @author liux (http://my.oschina.net/liux)
 * @version 1.0
 * @created 2012-3-21
 */
@SuppressWarnings("serial")
public abstract class Entity extends Base {

    @XStreamAlias("id")
    protected int id;

    public int getId() {
	return id;
    }

    public void setId(int id) {
	this.id = id;
    }

}
