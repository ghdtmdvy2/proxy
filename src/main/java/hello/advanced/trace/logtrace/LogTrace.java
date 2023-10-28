package hello.advanced.trace.logtrace;

import hello.advanced.trace.TraceStatus;

public interface LogTrace {
    TraceStatus begin(String message); // 로그 시작.
    void end(TraceStatus status); // 로그 끝.
    void exception(TraceStatus status, Exception e); // 에러 메시지.
}
