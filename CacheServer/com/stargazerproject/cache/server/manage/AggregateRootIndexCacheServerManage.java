package com.stargazerproject.cache.server.manage;

import com.google.common.base.Optional;
import com.google.common.util.concurrent.AbstractIdleService;
import com.google.common.util.concurrent.MoreExecutors;
import com.stargazerproject.service.annotation.ServiceZone;
import com.stargazerproject.service.annotation.Services;
import com.stargazerproject.service.baseinterface.StanderServiceShell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/** 
 *  @name aggregateRootIndexCache服务集中托管
 *  @illustrate aggregateRootIndexCache服务集中托管，继承于Guava的AbstractIdleService
 *  @author Felixerio
 *  **/
@Component(value="aggregateRootIndexCacheServerManage")
@Qualifier("aggregateRootIndexCacheServerManage")
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@Services(name="aggregateRootIndexCacheServerManage", serviceZone = ServiceZone.System, layer = 6)
public class AggregateRootIndexCacheServerManage extends AbstractIdleService{

	/** @illustrate aggregateRootIndexCacheServer的ServiceShell接口 **/
	@Autowired
	@Qualifier("aggregateRootIndexCacheServer")
	private StanderServiceShell aggregateRootIndexCacheServer;

	@Autowired
	@Qualifier("aggregateRootIndexCacheServerListener")
	private Listener workInServiceControlListener;

	/**
	* @name Springs使用的初始化构造
	* @illustrate
	*             @Autowired    自动注入
	*             @NeededInject 基于AOP进行最终获取时候的参数注入
	* **/
	@SuppressWarnings("unused")
	private AggregateRootIndexCacheServerManage() {}

	/**
	* @name 常规初始化构造
	* @illustrate 基于外部参数进行注入
	* **/
	public AggregateRootIndexCacheServerManage(Optional<StanderServiceShell> aggregateRootIndexCacheServerArg, Optional<Listener> workInServiceControlListenerArg) {
		aggregateRootIndexCacheServer = aggregateRootIndexCacheServerArg.get();
		workInServiceControlListener = workInServiceControlListenerArg.get();
	}
	
	/** @illustrate 类完成加载后将自动加载监听器 **/
	@PostConstruct
	private void addListener(){
		addListener(workInServiceControlListener, MoreExecutors.directExecutor());
	}
	
	/** @illustrate 启动服务及相关操作 **/
	@Override
	protected void startUp() throws Exception {
		aggregateRootIndexCacheServer.startUp();
	}
	
	/** @illustrate 关闭服务及相关操作 **/
	@Override
	protected void shutDown() throws Exception {
		aggregateRootIndexCacheServer.shutDown();
	}
	
}
