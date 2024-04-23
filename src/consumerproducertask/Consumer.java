package consumerproducertask;

import java.util.Random;

public class Consumer implements Runnable {
    private final Drop drop;

    public Consumer(Drop drop) {
        this.drop = drop;
    }

    public void run() {
        Random random = new Random();
        for (int num = drop.take(); num != -1; num = drop.take()) {
            System.out.format("NUMBER CONSUMED: %d%n\n", num);
            try {
                Thread.sleep(random.nextInt(1000));
            } catch (InterruptedException ignored) {}
        }
    }
}
