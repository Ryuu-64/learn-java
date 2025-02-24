package org.ryuu;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class LearnFuture {
    public static void main(String[] args) {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                0,
                16,
                20, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(64),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.CallerRunsPolicy()
        );

        List<CompletableFuture<Integer>> list = new ArrayList<>();
        for (int i = 0; i < 1_000; i++) {
            int finalI = i;
            CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(
                    () -> {
                        System.out.println(finalI);
                        return 1;
                    },
                    threadPoolExecutor
            );
            list.add(completableFuture);
        }

        //noinspection unchecked
        CompletableFuture<Integer>[] futures = list.toArray(new CompletableFuture[0]);
        CompletableFuture
                .allOf(futures)
                .join();
    }
}
