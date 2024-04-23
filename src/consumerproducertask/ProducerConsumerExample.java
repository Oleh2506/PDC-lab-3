package consumerproducertask;

public class ProducerConsumerExample {
    public static void main(String[] args) {
        Drop drop = new Drop();
        int ARR_LENGTH = 100;
        (new Thread(new Producer(drop, ARR_LENGTH))).start();
        (new Thread(new Consumer(drop))).start();
    }
}