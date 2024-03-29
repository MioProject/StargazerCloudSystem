package com.stargazerproject.analysis.resources.handle;

import com.google.common.base.Optional;
import com.stargazerproject.analysis.handle.EventResultsResultAnalysisHandle;
import com.stargazerproject.cache.Cache;
import com.stargazerproject.transaction.EventResultState;

public class EventResultsResultAnalysisHandleResources implements EventResultsResultAnalysisHandle {

    private Cache<String, String> resultCache;

    public EventResultsResultAnalysisHandleResources(Optional<Cache<String, String>> resultCacheArg){
        resultCache = resultCacheArg.get();
    }

    @Override
    public Optional<EventResultState> getTheLastEventResultState() {
        String eventResultState = resultCache.get(Optional.of("EventResultState_" + getRetryTime())).get();

        switch (eventResultState){
            case "FAULT":
                return Optional.of(EventResultState.FAULT);
            case "SUCCESS":
                return Optional.of(EventResultState.SUCCESS);
            case "WAIT":
                return Optional.of(EventResultState.WAIT);
            default:
                throw new IllegalArgumentException("EventResultState 无法转换");
        }

    }

    @Override
    public Optional<String> getTheLastErrorMessage() {
        return resultCache.get(Optional.of("ErrorMessage_" + getRetryTime()));
    }

    private int getRetryTime(){
        return Integer.parseInt(resultCache.get(Optional.of("RetryTime")).get());
    }
}