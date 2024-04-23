package consumerproducertask;

import java.util.Random;

public class Producer implements Runnable {
    private final Drop drop;
    private final int[] nums;

    public Producer(Drop drop, int len) {
        this.drop = drop;
        nums = new int[len];
        Random random = new Random();
        for (int i = 0; i < len; i++) {
            final int MAX_VALUE = 100;
            nums[i] = random.nextInt(MAX_VALUE + 1);
        }
    }

    public void run() {
        Random random = new Random();

        for (int num : nums) {
            drop.put(num);
            System.out.format("NUMBER PRODUCED: %d%n", num);
            try {
                Thread.sleep(random.nextInt(1000));
            } catch (InterruptedException ignored) {}
        }

        drop.put(-1);
    }
}