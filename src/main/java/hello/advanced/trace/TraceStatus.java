package hello.advanced.trace;

public class TraceStatus {
    private TraceId traceId; // 트랜잭션 Id
    private Long startTimeMs; // 걸린 시간.
    private String message; // 로그 메시지.

    public TraceStatus(TraceId traceId, Long startTimeMs, String message) {
        this.traceId = traceId;
        this.startTimeMs = startTimeMs;
        this.message = message;
    }
    public Long getStartTimeMs() {
        return startTimeMs;
    }
    public String getMessage() {
        return message;
    }
    public TraceId getTraceId() {
        return traceId;
    }
}
