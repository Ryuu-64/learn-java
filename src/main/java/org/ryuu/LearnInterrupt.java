package org.ryuu;

public class LearnInterrupt {
    public static void main(String[] args) {
        // 创建一个线程，模拟一个耗时操作
        Thread workerThread = new Thread(() -> {
            try {
                // 模拟一个长时间的阻塞操作
                System.out.println("Worker thread started. It's doing some work...");
                for (int i = 0; i < 5; i++) {
                    // 模拟每次执行的任务
                    System.out.println("Worker thread is working: " + (i + 1));

                    // 引发线程的阻塞，模拟实际工作的中断
                    Thread.sleep(1000); // 模拟每次任务执行时的延时
                }
            } catch (InterruptedException e) {
                // 捕获中断异常并恢复中断状态
                System.out.println("Worker thread was interrupted during sleep.");
                Thread.currentThread().interrupt();  // 恢复中断状态
            }

            // 检查是否被中断，如果中断了就退出
            if (Thread.currentThread().isInterrupted()) {
                System.out.println("Worker thread is exiting due to interruption.");
            }
        });

        // 启动工作线程
        workerThread.start();

        try {
            // 主线程让工作线程工作一段时间，然后中断它
            Thread.sleep(4000);  // 主线程等待 2 秒
            System.out.println("Main thread is interrupting the worker thread...");
            workerThread.interrupt();  // 中断工作线程
        } catch (InterruptedException e) {
            System.out.println("Main thread was interrupted.");
        }

        // 确保主线程等待工作线程执行完毕
        try {
            workerThread.join();  // 主线程等待工作线程结束
        } catch (InterruptedException e) {
            System.out.println("Main thread was interrupted while waiting for worker thread to finish.");
        }

        System.out.println("Main thread ends.");
    }
}
