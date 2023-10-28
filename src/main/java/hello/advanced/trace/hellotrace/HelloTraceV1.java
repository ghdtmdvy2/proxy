package hello.advanced.trace.hellotrace;

import hello.advanced.trace.TraceId;
import hello.advanced.trace.TraceStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class HelloTraceV1 {
    private static final String START_PREFIX = "-->"; // 시작 표시.
    private static final String COMPLETE_PREFIX = "<--"; // 완료 표시.
    private static final String EX_PREFIX = "<X-"; // 에러 표시.

    public TraceStatus begin(String message) {
        TraceId traceId = new TraceId(); // Trace 생성.
        Long startTimeMs = System.currentTimeMillis(); // 현재 시간 저장.
        log.info("[{}] {}{}", traceId.getId(), addSpace(START_PREFIX, traceId.getLevel()), message); // 로그 출력.
        return new TraceStatus(traceId, startTimeMs, message);
    }
    public TraceStatus beginSync(TraceId traceId, String message) {
        TraceId nextId = traceId.createNextId();
        Long startTimeMs = System.currentTimeMillis(); // 현재 시간 저장.
        log.info("[{}] {}{}", nextId.getId(), addSpace(START_PREFIX, nextId.getLevel()), message); // 로그 출력.
        return new TraceStatus(nextId, startTimeMs, message);
    }
    public void end(TraceStatus status) {
        complete(status, null);
    }
    public void exception(TraceStatus status, Exception e) {
        complete(status, e);
    }
    private void complete(TraceStatus status, Exception e) {
        Long stopTimeMs = System.currentTimeMillis(); // 현재시간 저장.
        long resultTimeMs = stopTimeMs - status.getStartTimeMs(); // 실행된 시간 저장.
        TraceId traceId = status.getTraceId();
        if (e == null) { // 에러가 생기지 않았을 때
            log.info("[{}] {}{} time={}ms", traceId.getId(),
                    addSpace(COMPLETE_PREFIX, traceId.getLevel()), status.getMessage(),
                    resultTimeMs);
        } else { // 에러가 생겼을 때.
            log.info("[{}] {}{} time={}ms ex={}", traceId.getId(),
                    addSpace(EX_PREFIX, traceId.getLevel()), status.getMessage(), resultTimeMs,
                    e.toString());
        }
    }

    private static String addSpace(String prefix, int level) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < level; i++) {
            // level 0:
            // level 1: |-->
            // level 2: | |-->
            sb.append( (i == level - 1) ? "|" + prefix : "| ");
        }
        return sb.toString();
    }
}
