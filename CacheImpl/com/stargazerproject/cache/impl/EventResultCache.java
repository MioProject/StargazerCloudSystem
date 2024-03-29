package com.stargazerproject.cache.impl;

import com.google.common.base.Optional;
import com.stargazerproject.annotation.description.NeedInitialization;
import com.stargazerproject.cache.Cache;
import com.stargazerproject.cache.base.impl.BaseCacheImpl;
import com.stargazerproject.interfaces.characteristic.shell.BaseCharacteristic;
import com.stargazerproject.interfaces.characteristic.shell.BeforehandCharacteristicShell;
import com.stargazerproject.interfaces.characteristic.shell.StanderCharacteristicShell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/** 
 *  @name 事件（BaseEvent）结果（ResultRecord）专用Cache
 *  @illustrate 事件（BaseEvent）结果专用Cache，用于初始化事件结果缓存的一些必要内容，
 *              EventResultCache需要初始化一些必要的键值对，初始化这些键值对需要在注解@NeedInitialization的参数中进行声明，
 *              注解@NeedInitialization的参数content（内容）中的键值对的声明方法为Json格式{"key1":"value","key2":"value"}
 *  @param : <String> 缓存的Key值类型
 *  @param : <String> 缓存的Value类型
 *  @author Felixerio
 *  @version 1.0.0
 *  **/
@Component(value="eventResultCache")
@Qualifier("eventResultCache")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@NeedInitialization(content = "{\"EventResultState_0\" : \"WAIT\"," /** @illustrate 结果状态（"Faile" Or "Success"）,初始状态为第0次 **/
		                    + " \"ErrorMessage_0\" : \"NULL\","      /** @illustrate 异常信息（NULL Or ExceptionMessage）,初始状态为第0次**/
		                    + " \"CompleteTime_0\" : \"0\","      /** @illustrate 完成时间（0 Or 格林威治时间（精确到微秒）） ,初始状态为第0次**/
		                    + " \"RetryTime\":\"0\"}")         /** @illustrate 重试次数（0 Or Int）,初始状态为0 **/
public final class EventResultCache extends BaseCacheImpl<String, String> implements StanderCharacteristicShell<Cache<String, String>>, BeforehandCharacteristicShell<Cache<String, String>> {

	private static final long serialVersionUID = 7696574737465211477L;

	/**
	* @name 常规初始化构造
	* @illustrate 基于外部参数进行注入
	* **/
	public EventResultCache() {
	}

	@Override
	public void initialize(Optional<Cache<String, String>> cacheArg) {
		cache = cacheArg.get();
		initiationParameters(this.getClass());
	}

	@Override
	@Qualifier("baseDataStructureCache")
	@Autowired
	public void initialize(BaseCharacteristic<Cache<String, String>> cacheArg) {
		cache = cacheArg.characteristic().get();
		initiationParameters(this.getClass());
	}
}