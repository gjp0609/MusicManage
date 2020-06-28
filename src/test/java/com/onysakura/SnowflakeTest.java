package com.onysakura;

import com.onysakura.utils.CustomLogger;
import com.onysakura.utils.SnowflakeIdWorker;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SnowflakeTest {
    private static final CustomLogger.Log LOG = CustomLogger.getLogger(SnowflakeTest.class);

    @Test
    public void test() throws InterruptedException {
        SnowflakeIdWorker idWorker = new SnowflakeIdWorker(0, 0);
        ConcurrentSkipListSet<Object> listSet = new ConcurrentSkipListSet<>();
        ExecutorService executorService = Executors.newFixedThreadPool(100);
        long millis = System.currentTimeMillis();
        for (int j = 0; j < 1000; j++) {
            executorService.submit(() -> {
                for (int i = 0; i < 1000; i++) {
                    long id = idWorker.nextId();
                    listSet.add(id);
                }
            });
        }
        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.MINUTES);
        LOG.info(System.currentTimeMillis() - millis);
        LOG.info(listSet.size());
    }
}
