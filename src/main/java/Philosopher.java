import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class Philosopher extends Thread {
    private String name;
    private int leftFork;
    private int rightFork;
    private int countEat;
    private Random random;
    private CountDownLatch countDownLatch;
    private Table table;

    public Philosopher(String name, Table table, int leftFork, int rightFork, CountDownLatch countDownLatch) {
        this.name = name;
        this.table = table;
        this.leftFork = leftFork;
        this.rightFork = rightFork;
        this.countDownLatch = countDownLatch;
        countEat = 0;
        random = new Random();
    }

    @Override
    public void run() {
        while (countEat < 3) {
            try {
                thinking();
                eating();
            } catch (InterruptedException e) {
                e.fillInStackTrace();
            }
        }

        System.out.println(name + " поел 3 раза");
        countDownLatch.countDown();
    }

    private void eating() throws InterruptedException {
        if (table.tryToGetFork(leftFork,rightFork)) {
            System.out.println(name + " ест двумя вилками, используя вилки: " + leftFork + " и " + rightFork);
            sleep(random.nextLong(3000, 6000));
            table.putForks(leftFork,rightFork);
            System.out.println(name + " поел и начал размышлять, положив вилки: " + leftFork + " и " + rightFork + " обратно на стол");
            countEat++;
        }
    }

    private void thinking() throws InterruptedException {
        sleep(random.nextLong(100, 2000));
    }
}
