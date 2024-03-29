package com.stargazerproject.transaction;

/** 
 *  @name 事件状态
 *  @illustrate 事件状态
 *              INIT ：    初始状态，事件为空
 *              WAIT ：    等待执行状态
 *              LINEUP ：  进入排队状态
	            RUN  ：    运行态
	            PASS ：    异常终止态，跳过此条指令
	            COMPLETE： 正常终止态，事件有可能执行成功或者失败
 *  @author Felixerio
 *  @version 1.0.0
 *  **/
public enum TransactionState {
	INIT,
	WAIT,
	LINEUP,
	PASS,
	COMPLETE,
}
