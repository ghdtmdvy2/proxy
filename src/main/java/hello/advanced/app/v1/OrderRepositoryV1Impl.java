package hello.advanced.app.v1;

public class OrderRepositoryV1Impl implements OrderRepositoryV1 {

    public void save(String itemId) {
        // 저장 로직.
        if (itemId.equals("ex")){
            throw new IllegalStateException("예외 발생!");
        }
        sleep(1000);
    }
    private void sleep(int millis){
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
