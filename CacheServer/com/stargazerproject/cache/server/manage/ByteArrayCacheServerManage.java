package com.stargazerproject.cache.server.manage;

import com.google.common.base.Optional;
import com.google.common.util.concurrent.AbstractIdleService;
import com.google.common.util.concurrent.MoreExecutors;
import com.stargazerproject.service.baseinterface.StanderServiceShell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.annotation.PostConstruct;

/** 
 *  @name ByteArrayCache服务集中托管
 *  @illustrate ByteArrayCache服务集中托管，继承于Guava的AbstractIdleService
 *  @author Felixerio
 *  **/
//@Component(value="byteArrayCacheServerManage")
//@Qualifier("byteArrayCacheServerManage")
//@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
//@Services(name="byteArrayCacheServerManage", serviceZone = ServiceZone.System, layer = 5)
public class ByteArrayCacheServerManage extends AbstractIdleService{
	
	/** @illustrate orderCacheServer的ServiceShell接口 **/
	@Autowired
	@Qualifier("byteArrayCacheServer")
	private StanderServiceShell byteArrayCacheServer;
	
	@Autowired
	@Qualifier("byteArrayCacheServerListener")
	private Listener workInServiceControlListener;
	
	/**
	* @name Springs使用的初始化构造
	* @illustrate 
	*             @Autowired    自动注入
	*             @NeededInject 基于AOP进行最终获取时候的参数注入
	* **/
	@SuppressWarnings("unused")
	private ByteArrayCacheServerManage() {}
	
	/**
	* @name 常规初始化构造
	* @illustrate 基于外部参数进行注入
	* **/
	public ByteArrayCacheServerManage(Optional<StanderServiceShell> byteArrayCacheServerArg, Optional<Listener> workInServiceControlListenerArg) {
		byteArrayCacheServer = byteArrayCacheServerArg.get();
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
		byteArrayCacheServer.startUp();
	}
	
	/** @illustrate 关闭服务及相关操作 **/
	@Override
	protected void shutDown() throws Exception {
		byteArrayCacheServer.shutDown();
	}

}
