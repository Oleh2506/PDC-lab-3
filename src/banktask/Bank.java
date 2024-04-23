package banktask;

import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.locks.ReentrantLock;

public class Bank {
    public static final int NTEST = 10000;
    private final int[] accounts;
    private final ReentrantLock lock;
    private final AtomicIntegerArray atomicAccounts;
    private boolean isTransferringInProcess;
    private long ntransacts;

    public Bank(int n, int initialBalance) {
        accounts = new int[n];
        int i;
        for (i = 0; i < accounts.length; i++) {
            accounts[i] = initialBalance;
        }
        ntransacts = 0;

        lock = new ReentrantLock();
        atomicAccounts = new AtomicIntegerArray(accounts);
        isTransferringInProcess = false;
    }

    public void transfer(int from, int to, int amount) {
        accounts[from] -= amount;
        accounts[to] += amount;
        ntransacts++;
        if (ntransacts % NTEST == 0) {
            test();
        }
    }

    public synchronized void syncMethodTransfer(int from, int to, int amount) {
        accounts[from] -= amount;
        accounts[to] += amount;
        ntransacts++;
        if (ntransacts % NTEST == 0) {
            test();
        }
    }

    public void syncBlockTransfer(int from, int to, int amount) {
        synchronized (accounts) {
            accounts[from] -= amount;
            accounts[to] += amount;
            ntransacts++;
            if (ntransacts % NTEST == 0) {
                test();
            }
        }
    }

    public void lockTransfer(int from, int to, int amount) {
        lock.lock();
        try {
            accounts[from] -= amount;
            accounts[to] += amount;
            ntransacts++;
            if (ntransacts % NTEST == 0) {
                test();
            }
        } finally {
            lock.unlock();
        }
    }

    public void atomicTransfer(int from, int to, int amount) {
        atomicAccounts.addAndGet(from, -amount);
        atomicAccounts.addAndGet(to, amount);

        lock.lock();
        try {
            ntransacts++;
            if (ntransacts % NTEST == 0) {
                int sum = 0;
                for (int i = 0; i < atomicAccounts.length(); i++) {
                    sum += atomicAccounts.get(i);
                }

                System.out.println("Transactions:" + ntransacts + " Sum: " + sum);
            }
        } finally {
            lock.unlock();
        }
    }

    public synchronized void waitNotifyTransfer(int from, int to, int amount) {
        while (isTransferringInProcess) {
            try {
                wait();
            } catch (InterruptedException ignored) {
            }
        }

        isTransferringInProcess = true;

        accounts[from] -= amount;
        accounts[to] += amount;
        ntransacts++;
        if (ntransacts % NTEST == 0) {
            test();
        }

        isTransferringInProcess = false;
        notifyAll();
    }

    public synchronized void test() {
        int sum = 0;
        for (int account : accounts) {
            sum += account;
        }
        System.out.println("Transactions:" + ntransacts+ " Sum: " + sum);
    }

    public int size() {
        return accounts.length;
    }
}
