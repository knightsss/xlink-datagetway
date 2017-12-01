package cn.xlink.cassandra.db.base;


import org.restexpress.plugin.hyperexpress.Linkable;

import com.strategicgains.repoexpress.domain.AbstractUuidEntity;
/**
 * 引入这个空的抽象类是为了方便扩展、更是为了以后方便更换存储框架
 * Created by Zhengzhenxie on 2017/8/15.
 */
public abstract class BaseEntity extends AbstractUuidEntity implements Linkable  {
	
}
