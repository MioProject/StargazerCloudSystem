package com.stargazerproject.transaction.impl.resources.shell;

import com.google.common.base.MoreObjects;
import com.google.common.base.MoreObjects.ToStringHelper;
import com.google.common.base.Optional;
import com.stargazerproject.analysis.TransactionAssembleAnalysis;
import com.stargazerproject.analysis.TransactionExecuteAnalysis;
import com.stargazerproject.analysis.TransactionResultAnalysis;
import com.stargazerproject.analysis.handle.TransactionAssembleAnalysisHandle;
import com.stargazerproject.analysis.handle.TransactionExecuteAnalysisHandle;
import com.stargazerproject.analysis.handle.TransactionResultAnalysisHandle;
import com.stargazerproject.interfaces.characteristic.shell.BaseCharacteristic;
import com.stargazerproject.transaction.*;
import com.stargazerproject.transaction.base.impl.ID;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/** 
 *  @name 事务（baseTransaction）实现
 *  @illustrate 事务（baseTransaction）是事件的原子聚合体
 *  @author Felixerio
 *  @version 1.0.0
 *  **/
@Component(value="baseTransactionShell")
@Qualifier("baseTransactionShell")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class BaseTransactionShell extends ID implements Transaction, BaseCharacteristic<Transaction>{
 
private static final long serialVersionUID = 5579247376914613210L;
	
	private Collection<Event> eventsList;

	private Result result;
	
	protected BaseTransactionShell() {}
	
	@Override
	public Optional<Transaction> characteristic() {
		return Optional.of(this);
	}
	
	/**
	* @name 事件生产，生产者调用
	* @illustrate 事件生产，生产者调用
	* @param transactionAssembleAnalysis 事务生产分析接口
	* **/
	@Override
	public Optional<TransactionAssembleAnalysisHandle> transactionAssemble(Optional<TransactionAssembleAnalysis> transactionAssembleAnalysis){
		return transactionAssembleAnalysis.get().analysis(Optional.of(eventsList));
	}
	
	/**
	* @name 事务结果分析，分析者接口
	* @illustrate 事务结果分析，分析者接口
	* @param transactionResultAnalysisArg 事务结果分析接口
	* **/
	@Override
	public Optional<TransactionResultAnalysisHandle> transactionResult(Optional<TransactionResultAnalysis> transactionResultAnalysisArg){
		List<EventResult> eventResultList = eventsList.stream().map(x -> (EventResult)x).collect(Collectors.toList());
		return result.resultResult(transactionResultAnalysisArg, eventResultList);
	}
	
	/**
	* @name 启动事务，运行者接口
	* @illustrate 启动事务，运行者接口
	* @param transactionExecuteAnalysis 事务运行分析接口
	* **/
	@Override
	public Optional<TransactionExecuteAnalysisHandle> transactionExecute(Optional<TransactionExecuteAnalysis> transactionExecuteAnalysis) {
		List<EventExecute> eventExecuteList = eventsList.stream().map(x -> (EventExecute)x).collect(Collectors.toList());
		return transactionExecuteAnalysis.get().analysis(Optional.of(eventExecuteList));
	}
	
	/**
	* @name 跳过此事务，通过调用其名下的Event的skipEvent方法来主动放弃Event的执行
	* @illustrate 跳过此事务，通过调用其名下的Event的skipEvent方法来主动放弃Event的执行
	* **/
	@Override
	public void skipTransaction() {
		eventsList.forEach(x -> x.skipEvent());
	}
	
	@Override
	public String toString() {
		ToStringHelper toStringHelper = MoreObjects.toStringHelper(this);
		eventsList.forEach(x -> toStringHelper.add("Events : ", x.toString()));
        return toStringHelper.toString();
	}

	
}
