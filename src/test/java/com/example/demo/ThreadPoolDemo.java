package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.*;

/**
 * @Author zhk
 * @Date 2021/11/29 15:49
 * @Version 1.0
 **/

@SpringBootTest
@Slf4j
public class ThreadPoolDemo {

    @Test
    public void threadPoolTest1() {

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        ThreadPoolExecutor pool = new ThreadPoolExecutor(10,10,
                1000, TimeUnit.MILLISECONDS,  new LinkedBlockingDeque<>(10));

        CompletableFuture<Void> task1 = CompletableFuture.runAsync(new Task(10), pool);
        /*CompletableFuture<Void> task2 = CompletableFuture.runAsync(new Task(8), pool);
        CompletableFuture<Void> task3 = CompletableFuture.runAsync(new Task(2), pool);
        CompletableFuture<Void> task4 = CompletableFuture.runAsync(new Task(4), pool);*/
        // wait for all threads finish
        //CompletableFuture.allOf(task1/*, task2, task3, task4*/).get();
        try {
            CompletableFuture.allOf(task1/*, task2, task3, task4*/).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println("start.............");
    }

    @Slf4j
    public static class Task implements Runnable {
        private final Integer count;

        public Task(Integer count) {
            this.count = count;
        }

        @Override
        public void run() {
            for (Integer i = count; i>=0; i--) {
                log.info("I'm counting {}", i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public static void main(String[] args) {
        MyRunnable command = new MyRunnable();
        long start = System.currentTimeMillis();
        int executeCount = 10;
        command.setEndListener(new MyRunnable.EndListener() {
            private int count = 0;

            @Override
            public void end() {
                count++;
                if (count == executeCount) {
                    long end = System.currentTimeMillis();
                    System.out.println("?????????????????????????????????:" + (end - start) + "??????");
                }
            }
        });
        System.out.println("????????????");

        //singleThread(command,executeCount);
        multiThread(command, executeCount, 100);
    }

    /**
     * ???????????????
     *
     * @param runnable     run
     * @param executeCount ????????????
     */
    public static void singleThread(MyRunnable runnable, int executeCount) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();//??????????????????
        for (int i = 0; i < executeCount; i++) {
            executorService.execute(runnable);
        }
    }

    /**
     * ???????????????
     *
     * @param runnable        run
     * @param executeCount    ????????????
     * @param coreThreadCount ??????????????????
     */
    public static void multiThread(MyRunnable runnable, int executeCount, int coreThreadCount) {
        BlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>();
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(coreThreadCount, 200, 2000, TimeUnit.SECONDS, queue);
        for (int i = 0; i < executeCount; i++) {
            threadPool.execute(runnable);
        }
    }

    public static class MyRunnable implements Runnable {
        private EndListener listener;
        private int count;

        @Override
        public void run() {
            try {
                int currentCount = 0;
                synchronized (MyRunnable.class) {
                    currentCount = count++;
                    System.out.println("?????????" + Thread.currentThread().getName() + "?????????" + currentCount + "?????????");
                }
                Thread.sleep(100);
                System.out.println("?????????" + Thread.currentThread().getName() + "???" + currentCount + "???????????????");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            listener.end();
        }

        public void setEndListener(EndListener listener) {
            this.listener = listener;
        }

        interface EndListener {
            void end();
        }
    }
}
