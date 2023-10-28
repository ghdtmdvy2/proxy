package hello.advanced.trace;

import java.util.UUID;

public class TraceId {
    private String id;
    private int level;

    public TraceId(){
        this.id = createId();
        this.level = 0;
    }
    public TraceId(String id, int level){
        this.id = id;
        this.level = level;
    }
    // TraceId 를 생성.
    private String createId() {
        return UUID.randomUUID().toString().substring(0,8);
    }

    // id 는 똑같고, trace level 을 올려주기.
    // -> 이렇게 함으로써 동일한 트랜잭션에서 이루어지고 있는 단계를 손쉽게 볼 수 있다.
    public TraceId createNextId(){
        return new TraceId(id, level + 1);
    }

    public TraceId createPrevId(){
        return new TraceId(id, level - 1);
    }

    // 첫번째 level 인지 확인.
    public boolean isFirstLevel(){
        return level == 0;
    }

    public String getId(){
        return id;
    }

    public int getLevel(){
        return level;
    }
}
