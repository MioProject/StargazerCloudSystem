package com.stargazerproject.transaction.impl.resources.shell;

import com.google.common.base.Optional;
import com.stargazerproject.analysis.EventResultAnalysis;
import com.stargazerproject.analysis.handle.EventResultAnalysisHandle;
import com.stargazerproject.cache.Cache;
import com.stargazerproject.interfaces.characteristic.shell.BaseCharacteristic;
import com.stargazerproject.transaction.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/** 
 *  @name 事件结果（BaseEventResult）实现
 *  @illustrate 事件结果是对于事务运行结果相关内容的聚合，包含了:
 *              executionResult : 运行结果缓存
 *  @author Felixerio
 *  @version 1.0.0
 *  **/
@Component(value="baseEventResultShell")
@Qualifier("baseEventResultShell")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class BaseEventResultShell implements Result<EventResultAnalysis, EventResultAnalysisHandle, Cache<String, String>, Cache<String, String>>, BaseCharacteristic<Result>{

	private static final long serialVersionUID = -4726816340050497590L;
	
	/**
	* @name 事件结果内容缓存
	* @illustrate 事件结果内容缓存
	* **/
	@Autowired
	@Qualifier("eventResultCache")
	private Cache<String, String> resultCache;

	/**
	* @name 常规初始化构造
	* @illustrate 基于外部参数进行注入
	* **/
	public BaseEventResultShell(Optional<Cache<String, String>> resultCacheArg) {
		resultCache = resultCacheArg.get();
	}
	
	/**
	* @name Springs使用的初始化构造
	* @illustrate 
	*             @Autowired    自动注入
	*             @NeededInject 基于AOP进行最终获取时候的参数注入
	* **/
	@SuppressWarnings("unused")
	private BaseEventResultShell(){}
	
	@Override
	public Optional<Result> characteristic() {
		return Optional.of(this);
	}

	/** @illustrate 事件结果内容分析器*/
	@Override
	public Optional<EventResultAnalysisHandle> resultResult(EventResultAnalysis eventResultAnalysis, Cache<String, String> patametersCacheArg, Cache<String, String> resultCacheArg) {
		return eventResultAnalysis.analysis(Optional.of(resultCache), Optional.of(patametersCacheArg), Optional.of(resultCacheArg));
	}

	@Override
	public void errorMessage(Optional<Exception> exception) {
		resultCache.put(Optional.of("ErrorMessage"), Optional.of(exception.get().getMessage()));
	}
	
	@Override
	public boolean sameValueAs(Result other) {
		return false;
	}

}
