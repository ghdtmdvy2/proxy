package hello.advanced.trace.template;

import hello.advanced.trace.TraceStatus;
import hello.advanced.trace.logtrace.LogTrace;

// 제너릭으로 생성 해주기.
// 제너릭이란 간단하게 객체 생성 시 타입을 지정하게하여 생성 해주는 것.
public abstract class AbstractTemplate<T> {
    private final LogTrace trace;

    protected AbstractTemplate(LogTrace trace) {
        this.trace = trace;
    }
    public T execute(String message){
        TraceStatus status = null;
        try {
            status = trace.begin(message);
            // 로직 호출
            T result = call();
            trace.end(status);
            return result;
        } catch (Exception e) {
            trace.exception(status,e);
            throw e;
        }
    }

    protected abstract T call();
}
