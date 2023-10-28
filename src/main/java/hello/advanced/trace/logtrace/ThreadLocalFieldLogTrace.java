package hello.advanced.trace.logtrace;

import hello.advanced.trace.TraceId;
import hello.advanced.trace.TraceStatus;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ThreadLocalFieldLogTrace implements LogTrace {
    private static final String START_PREFIX = "-->"; // 시작 표시.
    private static final String COMPLETE_PREFIX = "<--"; // 완료 표시.
    private static final String EX_PREFIX = "<X-"; // 에러 표시.

    private ThreadLocal<TraceId> traceIdHolder = new ThreadLocal<>(); // traceId 를 동기화, 동시성 이슈 발생.

    @Override
    public TraceStatus begin(String message) {
        syncTraceId(); // TraceId 생성.
        TraceId traceId = traceIdHolder.get(); // syncTraceId로 생성된 TraceId 로 저장.
        Long startTimeMs = System.currentTimeMillis(); // 현재 시간 저장.
        log.info("[{}] {}{}", traceId.getId(), addSpace(START_PREFIX, traceId.getLevel()), message); // 로그 출력.
        return new TraceStatus(traceId, startTimeMs, message);
    }

    private void syncTraceId(){
        TraceId traceId = traceIdHolder.get();
        if (traceId == null) { // TraceId 가 없을 때는 새로 생성.
            traceIdHolder.set(new TraceId());
        } else { // TraceId 가 있을 때는 level 증가.
            traceIdHolder.set(traceId.createNextId());
        }
    }
    @Override
    public void end(TraceStatus status) {
        complete(status, null);
    }
    @Override
    public void exception(TraceStatus status, Exception e) {
        complete(status, null);
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
        releaseTraceId();
    }

    private void releaseTraceId(){ // traceId 의 level 을 낮춰주는 것.
        TraceId traceId = traceIdHolder.get();
        if (traceId.isFirstLevel()) { // 첫번째 level 인 경우 TraceId 파괴
            traceIdHolder.remove();
        } else {
            traceIdHolder.set(traceId.createPrevId()); // level 을 낮춰주기.
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
