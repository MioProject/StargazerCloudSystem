package com.stargazerproject.transaction.base.impl;

import com.google.common.base.Optional;
import com.stargazerproject.transaction.Entity;

/** 
 *  @name ID对象（可追踪实体根对象）
 *  @illustrate ID对象是所有可跟踪实体对象的根对象
 *  @author Felixerio
 *  @version 1.0.0
 *  **/
//@Component
//@Qualifier("iD")
//@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ID implements Entity<String>{
	
	private static final long serialVersionUID = -8739771505481213633L;
	
	/** @illustrate ID**/
	private String id;
	
	protected ID() {}
	
	/**
	* @name 获取ID
	* @illustrate  获取ID
	* @return Optional<String> sequenceID ： ID值
	* **/
	@Override
	public Optional<String> sequenceID(){
		return Optional.of(id);
	}

	/**
	* @name 注入ID
	* @illustrate  注入ID
	* **/
	@Override
	public void injectSequenceID(Optional<String> idArg) {
		id = idArg.get();
	}
}
