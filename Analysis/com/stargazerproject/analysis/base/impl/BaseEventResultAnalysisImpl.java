package com.stargazerproject.analysis.base.impl;

import com.google.common.base.Optional;
import com.stargazerproject.analysis.EventResultAnalysis;
import com.stargazerproject.analysis.handle.EventResultAnalysisHandle;
import com.stargazerproject.cache.Cache;

public abstract class BaseEventResultAnalysisImpl implements EventResultAnalysis {

	protected EventResultAnalysis eventResultAnalysis;
	
	@Override
	public Optional<EventResultAnalysisHandle> analysis(Optional<Cache<String, String>> executionResultCache) {
		return eventResultAnalysis.analysis(executionResultCache);
	}

}
