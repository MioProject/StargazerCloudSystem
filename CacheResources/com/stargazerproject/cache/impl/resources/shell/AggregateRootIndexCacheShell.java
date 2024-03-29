package com.stargazerproject.cache.impl.resources.shell;

import com.google.common.base.Optional;
import com.google.common.cache.LoadingCache;
import com.stargazerproject.annotation.description.ThreadSafeLevel;
import com.stargazerproject.annotation.description.ThreadSafeMethodsLevel;
import com.stargazerproject.cache.Cache;
import com.stargazerproject.interfaces.characteristic.shell.BaseCharacteristic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ExecutionException;

/** 
 *  @name AggregateRootIndex缓存
 *  @Shell AggregateRootIndex<K, V> loadingCache，Guava LoadingCache 类型的通用接口
 *  @illustrate 在指定条件下发生解出操作的缓存，
 *              1.自主解出
 *              2.超时间解出
 *              3.超容量解出
 *  @param :<K> 缓存的Key值类型
 *  @param :<V> 缓存的Value类型
 *  @author Felixerio
 *  **/
@Component(value="aggregateRootIndexCacheShell")
@Qualifier("aggregateRootIndexCacheShell")
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class AggregateRootIndexCacheShell implements  Cache<String, Cache<String, String>>, BaseCharacteristic<Cache<String, Cache<String, String>>>{

	private static final long serialVersionUID = 4246306092901363300L;

	@Autowired
	@Qualifier("aggregateRootIndexCacheLoadingCacheCharacteristic")
	private BaseCharacteristic<LoadingCache<String,Cache<String, String>>> aggregateRootIndexCacheLoadingCacheCharacteristic;

	/** @illustrate 通用LoadingCache Guava 缓存接口 **/
	protected LoadingCache<String, Cache<String, String>> loadingCache;

	/**
	* @name Springs使用的初始化构造
	* @illustrate
	*             @Autowired    自动注入
	*             @NeededInject 基于AOP进行最终获取时候的参数注入
	* **/
	@SuppressWarnings("unused")
	private AggregateRootIndexCacheShell() {}

	/**
	* @name 常规初始化构造
	* @illustrate 基于外部参数进行注入
	* **/
	public AggregateRootIndexCacheShell(Optional<BaseCharacteristic<LoadingCache<String,Cache<String, String>>>> aggregateRootIndexCacheLoadingCacheCharacteristicArg) {
		aggregateRootIndexCacheLoadingCacheCharacteristic = aggregateRootIndexCacheLoadingCacheCharacteristicArg.get();
	}
	
	@ThreadSafeMethodsLevel(threadSafeLevel = ThreadSafeLevel.ThreadCompatible)
	@Override
	public Optional<Cache<String, Cache<String, String>>> characteristic() {
		loadingCache = aggregateRootIndexCacheLoadingCacheCharacteristic.characteristic().get();
		return Optional.of(this);
	}
	
	/**
	 * @name 置入
	 * @illustrate 缓存内容置入,Key及Value均不允许空值
	 * @param @Optional <String> Guava包装缓存的Key值，不允许空值
	 * @param @Optional <Transaction> Guava包装缓存的Value值，不允许空值
	 * @ThreadSafeMethodsLevel put方法的线程安全级别是 ThreadSafeLevel.ThreadSafe，安全的线程安全方法
	 * **/
	@ThreadSafeMethodsLevel(threadSafeLevel = ThreadSafeLevel.ThreadSafe)
	@Override
	public void put(Optional<String> key, Optional<Cache<String, String>> value) {
		loadingCache.put(key.get(), value.get());
	}

	/**
	 * @name 获取
	 * @illustrate 缓存内容获取
	 * @param @Optional <String> Guava包装缓存的Key值，不允许空值
	 * @return @Optional <Transaction> Guava包装缓存的Value值，如果Key值没有对应的Value，则返回Optional的空值包装模式
	 * @ThreadSafeMethodsLevel get方法的线程安全级别是 ThreadSafeLevel.ThreadSafe，安全的线程安全方法
	 * **/
	@ThreadSafeMethodsLevel(threadSafeLevel = ThreadSafeLevel.ThreadSafe)
	@Override
	public Optional<Cache<String, String>> get(Optional<String> key) {
		try {
			return Optional.of(loadingCache.get(key.get()));
		} catch (ExecutionException e) {
			throw new NullPointerException("Stargazer ServiceControlServer Report :  Key : "+key.get()+" Value is Null");
		}
	}

	/**
	 * @name 移除
	 * @illustrate 移除缓存内容
	 * @param @Optional <String> Guava包装缓存的Key值，不允许空值
	 * @return @Optional <String> Guava包装缓存的Boolean值，因为Guava LoadingCache的invalidate操作没有返回值，所以本函数返回值永远为True
	 * @ThreadSafeMethodsLevel remove方法的线程安全级别是 ThreadSafeLevel.ThreadSafe，安全的线程安全方法
	 * **/
	@ThreadSafeMethodsLevel(threadSafeLevel = ThreadSafeLevel.ThreadSafe)
	@Override
	public Optional<Boolean> remove(Optional<String> key) {
		loadingCache.invalidate(key.get());
		return Optional.of(Boolean.TRUE);
	}
	
	/**
	 * @name 清除
	 * @illustrate 清除缓存所有内容
	 * @ThreadSafeMethodsLevel remove方法的线程安全级别是 ThreadSafeLevel.ThreadSafe，安全的线程安全方法
	 * **/
	@ThreadSafeMethodsLevel(threadSafeLevel = ThreadSafeLevel.ThreadSafe)
	public void clear(){
		loadingCache.cleanUp();
	}
	
	/**
	 * @name 获取结果集
	 * @illustrate 获取Set类型的结果集，结果集是无序的
	 * @ThreadSafeMethodsLevel entrySet方法的线程安全级别是 ThreadSafeLevel.ThreadSafe，安全的线程安全方法
	 * **/
	@ThreadSafeMethodsLevel(threadSafeLevel = ThreadSafeLevel.ThreadSafe)
	public Optional<Set<Entry<String, Cache<String, String>>>> entrySet(){
		return ( Optional.of(loadingCache.asMap().entrySet()) );
	}

}